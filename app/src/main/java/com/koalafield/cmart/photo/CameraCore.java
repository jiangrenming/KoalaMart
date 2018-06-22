package com.koalafield.cmart.photo;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


public class CameraCore{
    //调用系统相机的Code
    public static final int REQUEST_TAKE_PHOTO_CODE = 1001;
    //拍照裁剪的Code
    public static final int REQUEST_TAKE_PHOTO_CROP_CODE = 1003;
    //调用系统图库的Code
    public static final int REQUEST_TAKE_PICTRUE_CODE = 1002;
    //调用系统图库裁剪Code
    public static final int REQUEST_TAKE_PICTRUE_CROP_CODE = 1004;
    //裁剪的Code
    public static final int REQUEST_TAKE_CROP_CODE = 1005;
    //截取图片的高度
    public static final int REQUEST_HEIGHT = 648;
    //截取图片的宽度
    public static final int REQUEST_WIDTH = 608;

    //存储图片地址
    private Uri photoURL;

    private Activity mActivity;
    private PhotoCallback cameraResult;

    public CameraCore(PhotoCallback cameraResult,Activity activity){
        this.cameraResult = cameraResult;
        this.mActivity = activity;

    }

    public Uri getPhotoUri(){
        return this.photoURL;
    }

    public void setPhotoUri(Uri uri){
        this.photoURL = uri;
    }
    /**
     * 调取拍照
     * @param uri
     * @return
     */
    protected Intent startTake_Photo(Uri uri){

        if (Build.VERSION.SDK_INT >= 23) {
            this.photoURL = TUriParse.convertFileUriToFileProviderUri(mActivity, uri);
        } else {
            this.photoURL = uri;
        }
        Intent captureIntent = getCaptureIntent(photoURL);
        return  captureIntent;
    }

    /**
     * 拍照并裁剪
     */
    protected Intent startTakePhone_Crop(Uri uri){

        if (Build.VERSION.SDK_INT >= 23) {
            this.photoURL = TUriParse.getTempUri(mActivity);
        } else {
            this.photoURL = uri;
        }
        Intent captureIntent = getCaptureIntent(photoURL);
        return  captureIntent;
    }

    /**
     * 获取拍照的Intent
     * @return
     */
    public static Intent getCaptureIntent(Uri outPutUri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍取的照片保存到指定URI
        return intent;
    }

    //调用系统相册
    protected Intent startTakePicture(Uri uri){
        this.photoURL = uri;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURL);
        intent.setType("image/*");//从所有图片中进行选择
        return intent;
    }

    //调用系统裁剪图片，对Intent参数进行封装
    protected Intent takeCropPicture(Uri photoURL, int with, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoURL, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", with);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURL);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    //拍照不裁剪
    public void getPhotoFromCamera(Uri uri) {
        mActivity.startActivityForResult(startTake_Photo(uri),REQUEST_TAKE_PHOTO_CODE);
    }

    //拍照裁剪
    public void getPhotoFromCameraCrop(Uri uri) {
        mActivity.startActivityForResult(startTakePhone_Crop(uri),REQUEST_TAKE_PHOTO_CROP_CODE);
    }

    //相册选择不裁剪
    public void getPhotoFromAlbum(Uri uri) {
        mActivity.startActivityForResult(startTakePicture(uri),REQUEST_TAKE_PICTRUE_CODE);
    }

    //相册选择裁剪
    public void getPhotoFromAlbumCrop(Uri uri) {
        mActivity.startActivityForResult(startTakePicture(uri),REQUEST_TAKE_PICTRUE_CROP_CODE);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PICTRUE_CODE){
                //获取系统返回的照片的Uri
                photoURL = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                //从系统表中查询指定Uri对应的照片
                Cursor cursor = mActivity.getContentResolver().query(photoURL, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);  //获取照片路径
                cursor.close();
                if (!TextUtils.isEmpty(picturePath)) {
                    cameraResult.onSuccess(picturePath,REQUEST_TAKE_PICTRUE_CODE);
                } else {
                    cameraResult.onFail("文件没找到",REQUEST_TAKE_PICTRUE_CODE);
                }
            }else if (requestCode == REQUEST_TAKE_PICTRUE_CROP_CODE){
                photoURL = data.getData();
                mActivity.startActivityForResult(takeCropPicture(photoURL, REQUEST_HEIGHT,
                        REQUEST_WIDTH), REQUEST_TAKE_CROP_CODE);
            }else if (requestCode == REQUEST_TAKE_PHOTO_CODE){
                cameraResult.onSuccess(photoURL.getPath(),REQUEST_TAKE_PHOTO_CODE);
            }else  if (requestCode == REQUEST_TAKE_PHOTO_CROP_CODE){
                mActivity.startActivityForResult(takeCropPicture(Uri.fromFile(new File(photoURL.getPath())),
                        REQUEST_HEIGHT, REQUEST_WIDTH), REQUEST_TAKE_CROP_CODE);
            }else if (requestCode == REQUEST_TAKE_CROP_CODE){
                String path = getPicFromUri(photoURL, mActivity);
                cameraResult.onSuccess(path,REQUEST_TAKE_CROP_CODE);
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            cameraResult.onFail("取消获取图片",requestCode);
        }
    }

    public String getPicFromUri(Uri uri,Context context){
        try {
            Activity ac = (Activity) context;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = ac.managedQuery(uri, proj, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return uri.getPath();
        }
    }
}
