package com.zennex.task.module.frg_scaling_detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnBackPressedListener;
import com.zennex.task.common.mvp.BaseFragment;
import com.zennex.task.common.utils.BundleUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ScalingDetailFragment extends BaseFragment implements ScalingDetailContract.View, OnBackPressedListener {

    public static final String TAG = "tag_scaling_detail_frg";
    private final float CONST_COUNT_STEP = 10;

    @BindView(R.id.frg_scaling_detail_iv_picture) ImageView ivScaling;
    private PhotoViewAttacher attacher;
    private Bitmap bitmap = null;
    private float stepZoom;

    @Inject ScalingDetailPresenter presenter;
    private ScalingDetailRouter router;

    // region - Lifecycle -

    public static ScalingDetailFragment newInstance(Bitmap bitmap) {
        ScalingDetailFragment frg = new ScalingDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleUtils.IMAGE_BITMAP_KEY, bitmap);
        frg.setArguments(bundle);

        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router = (ScalingDetailRouter) activity;
        getFragmentComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root;
        root = inflater.inflate(R.layout.frg_scaling_detail, container, false);

        ButterKnife.bind(this, root);

        Bundle bundle = getArguments();
        if (bundle != null) {
            bitmap = bundle.getParcelable(BundleUtils.IMAGE_BITMAP_KEY);
        }

        if (savedInstanceState != null) {
            bitmap = savedInstanceState.getParcelable(BundleUtils.IMAGE_BITMAP_KEY);
        }

        initScreen();


        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bitmap != null) outState.putParcelable(BundleUtils.IMAGE_BITMAP_KEY, bitmap);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    // endregion


    // region - Event handlers -

    @OnClick(R.id.frg_scaling_detail_btn_zoom_in)
    public void clickBtnZoomIn(View view) {
        presenter.onClickBtnZoomIn();
    }

    @OnClick(R.id.frg_scaling_detail_btn_zoom_out)
    public void clickBtnZoomOut(View view) {
        presenter.onClickBtnZoomOut();
    }

    @Override
    public void onBackPressed() {
        router.navigateToScalingMainScreen();
    }

    // endregion


    // region - Contract -

    // endregion


    // region - Methods -

    private void initScreen() {
        setTextTitleToolbar(getString(R.string.title_scaling_detail));
        if (bitmap != null) initImageBitmap();
    }

    private void initImageBitmap() {
        ivScaling.setImageBitmap(bitmap);
        attacher = new PhotoViewAttacher(ivScaling);
        attacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
        stepZoom = (attacher.getMaximumScale() - attacher.getMinimumScale()) / CONST_COUNT_STEP;
    }

    @Override
    public void reduceZoom() {
        float scale = (float) (attacher.getScale() - stepZoom);
        attacher.setScale(scale, true);
    }

    @Override
    public void increaseZoom() {
        float scale = (float) (attacher.getScale() + stepZoom);
        attacher.setScale(scale, true);
    }

    // endregion
}
