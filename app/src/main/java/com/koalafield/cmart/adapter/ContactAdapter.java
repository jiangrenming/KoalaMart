package com.koalafield.cmart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.koalafield.cmart.R;
import com.koalafield.cmart.bean.user.CountryCode;
import com.koalafield.cmart.utils.ContactComparator;
import com.koalafield.cmart.utils.PinUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/3.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CountryCode> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    List<CountryCode> countryCodes;
    private List<String> mContactList; // 联系人名称List（转换成拼音）



    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public ContactAdapter(Context context, List<CountryCode> countryCodes) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.countryCodes = countryCodes;
        handleContact();
    }

    private void handleContact() {
        mContactList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < countryCodes.size(); i++) {
            String pinyin = PinUtils.getPingYin(countryCodes.get(i).getCountry());
            map.put(pinyin, countryCodes.get(i).getCountry());
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());
        resultList = new ArrayList<>();
        characterList = new ArrayList<>();
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                    resultList.add(new CountryCode(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new CountryCode("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            resultList.add(new CountryCode(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            View view = mLayoutInflater.inflate(R.layout.item_character, parent, false);
            return new CharacterHolder(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.item_contact, parent, false);
            return new ContactHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getCountry());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).mTextView.setText(resultList.get(position).getCountry());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }


    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CharacterHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextView;
        ContactHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.contact_name);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener!=null){
                mItemClickListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
    /**
     * 切换滚动
     * @param character
     * @return
     */
    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getCountry().equals(character)) {
                    return i;
                }
            }
        }
        return -1; // -1不会滑动
    }

    private OnItemClickListener mItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
