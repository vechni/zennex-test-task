package com.zennex.task.module.frg_scaling;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScalingMainFragment extends BaseFragment implements ScalingMainContract.View, OnBackPressedListener {

    public static final String TAG = "tag_scaling_main_frg";
    private final static int PICK_GALLERY_REQUEST = 1;
    private final static int PICK_CAMERA_REQUEST = 2;

    @Inject ScalingMainPresenter presenter;
    private ScalingMainRouter router;

    // region - Lifecycle -

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (ScalingMainRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;
        root = inflater.inflate(R.layout.frg_scaling, container, false);

        ButterKnife.bind(this, root);

        initScreen();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    // endregion


    // region - Event handlers -

    @OnClick(R.id.frg_scaling_btn_gallery)
    public void clickBtnPickImageGallery(View view) {
        presenter.onClickPickImageGallery();
    }

    @OnClick(R.id.frg_scaling_btn_camera)
    public void clickBtnPickImageCamera(View view) {
        presenter.onClickPickImageCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if (requestCode == PICK_GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {

            Uri uri = data.getData();
            if (uri != null) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                    router.navigateToScalingDetailScreen(bitmap);
                } catch (IOException e) {
                    showToastShort(getString(R.string.txt_error));
                }
            }
        } else if (requestCode == PICK_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            router.navigateToScalingDetailScreen(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        router.navigateToMainScreen();
    }

    // endregion


    // region - Contract -

    @Override
    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.message_select_picture)), PICK_GALLERY_REQUEST);
    }

    @Override
    public void pickImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_CAMERA_REQUEST);
    }

    // endregion


    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_scaling));
    }

    // endregion
}