package com.iambedant.nanodegree.quickbite.ui.settings;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

/**
 * Created by iamBedant on 7/12/2016.
 */
public class SettingsPresenter extends BasePresenter<SettingsMvpView> {
    String TAG = SettingsPresenter.class.getSimpleName();
    private final DataManager mDataManager;

    @Override
    public void attachView(SettingsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();

    }


    @Inject
    public SettingsPresenter(DataManager dataManager) {
        Logger.d(TAG, "Presenter Created");
        mDataManager = dataManager;
    }

    public void logout() {
        mDataManager.logoutUser();
    }

    public void updatePassword(String oldPassword, String newPassword) {
        mDataManager.updatePassword(oldPassword, newPassword);
    }

    public void updateName(String name) {
        mDataManager.updateName(name);
    }

    public int getProvider() {
        return mDataManager.getProviderType();
    }
}
