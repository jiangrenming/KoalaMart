package com.koalafield.cmart.photo;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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

    //调用系统拍照
    protected Intent startTakePhoto(Uri uri){
 //      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
        /***
         * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
         * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
         * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
         */
       /* ContentValues values = new ContentValues();
        photoURL = mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
       intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURL);*/
        this.photoURL = uri;
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURL);//将拍取的照片保存到指定URI
        Log.i("存储的地址:",photoURL.toString());
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

    public void getPhotoFromCamera(Uri uri) {
        mActivity.startActivityForResult(startTakePhoto(uri),REQUEST_TAKE_PHOTO_CODE);
    }

    public void getPhotoFromCameraCrop(Uri uri) {
        mActivity.startActivityForResult(startTakePhoto(uri),REQUEST_TAKE_PHOTO_CROP_CODE);
    }

    public void getPhotoFromAlbum(Uri uri) {
        mActivity.startActivityForResult(startTakePicture(uri),REQUEST_TAKE_PICTRUE_CODE);
    }

    public void getPhotoFromAlbumCrop(Uri uri) {
        mActivity.startActivityForResult(startTakePicture(uri),REQUEST_TAKE_PICTRUE_CROP_CODE);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.i("获取图片的地址",photoURL.getPath());
            Log.i("获取图片的code=",requestCode+"");
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
