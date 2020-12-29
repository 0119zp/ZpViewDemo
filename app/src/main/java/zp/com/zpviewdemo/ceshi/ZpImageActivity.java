package zp.com.zpviewdemo.ceshi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import zp.com.zpbase.utils.ZpFileUtil;
import zp.com.zpbase.utils.ZpImageUtil;

/**
 * Created by Administrator on 2017/10/14 0014.
 */

public class ZpImageActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 保存图片
     *
     * @param url url
     */
    public void saveImage(String url, Context context) {
        Uri uri = Uri.parse(url);
        String base64 = uri.getQueryParameter("base64");
        String imgUrl = uri.getQueryParameter("url");
        if (!TextUtils.isEmpty(base64)) {
            Bitmap bitmap = ZpImageUtil.stringToBitmap(base64);
            saveBase64Img(context, bitmap);
        } else if (!TextUtils.isEmpty(imgUrl)) {
            saveUrlImg(context, imgUrl);
        }
    }

    private void saveBase64Img(Context context, Bitmap bitmap) {
        if (bitmap != null) {
            if (ZpFileUtil.saveBitmap(context, bitmap, "", "image_" + System.nanoTime()) != null) {
                Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
                bitmap.recycle();
                bitmap = null;
            } else {
                Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show();
                bitmap.recycle();
                bitmap = null;
            }
        } else {
            Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUrlImg(final Context context, String url) {
        // 根据picaso将图片地址转换为bitmap对象，然后保存
        // Picasso.with(context).load(url).into(new Target() {  ... }

    }


}
