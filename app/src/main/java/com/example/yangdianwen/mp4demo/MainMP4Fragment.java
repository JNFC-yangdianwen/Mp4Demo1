package com.example.yangdianwen.mp4demo;


import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by yangdianwen on 16-7-26.
 */
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener {
    private TextureView textureView;//用来播放视频的view
    private MediaPlayer mediaPlayer;//

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        textureView=new TextureView(getContext());
        return textureView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textureView.setSurfaceTextureListener(this);
    }
    //的确准备好了
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        try {
            //获取资源
            AssetFileDescriptor fd = getContext().getAssets().openFd("welcome.mp4");
            FileDescriptor descriptor = fd.getFileDescriptor();
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(descriptor,fd.getStartOffset(),fd.getLength());
            mediaPlayer.prepareAsync();//异步
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Surface mpSurface=new Surface(surface);
                    mediaPlayer.setSurface(mpSurface);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
          e.printStackTrace();
        }


    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
