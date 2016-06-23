package mobapptut.com.zambelislightscamapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import zambelislights.com.cameraapp.R;

public class zambelislightscamapp extends Activity {

    private static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = 1;
    private TextureView mTextureView;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setupCamera(width, height);
            connectCamera();
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
    };
    private CameraDevice mCameraDevice; private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {


        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();
//             Toast.makeText(getApplicationContext(),
//                     "Camera connection made!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };



    private HandlerThread mBackgroundHandlerThread;
    private Handler mBackgroundHandler;
    private String mCameraId;
    private Size mPreviewSize;
    private Size mVideoSize;
    private MediaRecorder mMediaRecorder;
    private int mTotalRotation;
    private CaptureRequest.Builder mCaptureRequestBuilder;

    private ImageButton mRecordImageButton;
    //silvia button #1
    private ImageButton clickButtonPlusImg;
    private ImageButton clickButtonMinusImg;
    private Button backButton;
    private boolean mIsRecording = false;

    private File mVideoFolder;
    private String mVideoFileName;

    private Button btnCapture = null;
    private Uri file;

    private static SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    private static class CompareSizeByArea implements Comparator<Size> {

        @Override @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public int compare(Size lhs, Size rhs) {


            return Long.signum((long) lhs.getWidth() * lhs.getHeight() /
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }
    int windowwidth;
    int windowheight;

    private LinearLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2_video_image);

//START
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();
        final ImageView img = (ImageView) findViewById(R.id.ImageView01);

        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img
                        .getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        }

                        layoutParams.leftMargin = x_cord - 1;
                        layoutParams.topMargin = y_cord - 1;

                        img.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

//END

        // check version
        if(Build.VERSION.SDK_INT <= 4.0){
            //this code will be executed on devices running on DONUT (NOT ICS) or later

            Context context = getApplicationContext();
            CharSequence text = "Bellow 4.0";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
        }else
        {
          // Toast.makeText(getApplicationContext(), "Above 4.0!", Toast.LENGTH_SHORT).show();
        }



        createVideoFolder();
        createImageFolder();


        mMediaRecorder = new MediaRecorder();

        mTextureView = (TextureView) findViewById(R.id.textureView);
        //mRecordImageButton = (ImageButton) findViewById(R.id.videoOnlineImageButton);
//        mRecordImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mIsRecording) {
//                    mIsRecording = false;
//                    //mRecordImageButton.setImageResource(R.mipmap.btn_video_online);
//                } else {
//                    checkWriteStoragePermission();
//                }
//            }
//        });

        //silvia button
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"Button clicked!",Toast.LENGTH_SHORT).show();
                Intent backtoHome = new Intent(zambelislightscamapp.this, CategoryActivity.class);
                startActivity(backtoHome);
                finish();
            }
        });

        clickButtonPlusImg = (ImageButton) findViewById(R.id.plusBtnImg);
        clickButtonPlusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"clicked!", Toast.LENGTH_SHORT).show();

                // image that will be resized
                ImageView yourImageView= (ImageView)findViewById(R.id.ImageView01);

                // final android.view.ViewGroup.LayoutParams layoutParams = yourImageView.getLayoutParams();
                //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(30, 30);
                //layoutParams.width += 32;
                // layoutParams.height += 32;
                //width and height of your Image ,if it is inside Relative change the LinearLayout to RelativeLayout.
                //yourImageView.setLayoutParams(layoutParams);

                float x = yourImageView.getScaleX();
                float y = yourImageView.getScaleY();

                String sil = Float.toString(x);
                String maxreach = "Max Size reached!";
                //Toast.makeText(getApplicationContext(), sil, Toast.LENGTH_SHORT).show();

                if( sil.equals("1.5")) {
                    //.makeText(getApplicationContext(), maxreach, Toast.LENGTH_SHORT).show();
                }else{
                    yourImageView.setScaleX((float) (x + 0.5));
                    yourImageView.setScaleY((float) (y + 0.5));
                    //Toast.makeText(getApplicationContext(), sil, Toast.LENGTH_SHORT).show();
                }



            }
        });

        clickButtonMinusImg = (ImageButton) findViewById(R.id.minusBtnImg);
        clickButtonMinusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),
//                        "clicked!", Toast.LENGTH_SHORT).show();

                // image that will be resized
                ImageView yourImageView= (ImageView)findViewById(R.id.ImageView01);

                //final android.view.ViewGroup.LayoutParams layoutParams = yourImageView.getLayoutParams();
                //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(30, 30);
                //layoutParams.width -= 32;
                //layoutParams.height -= 32;
                //width and height of your Image ,if it is inside Relative change the LinearLayout to RelativeLayout.
                //yourImageView.setLayoutParams(layoutParams);
                float x = yourImageView.getScaleX();
                float y = yourImageView.getScaleY();




                String sil = Float.toString(x);
                String minreach = "Minimum Size reached!";
                //Toast.makeText(getApplicationContext(), sil, Toast.LENGTH_SHORT).show();

                if( sil.equals("0.5")) {
                    //Toast.makeText(getApplicationContext(), minreach, Toast.LENGTH_SHORT).show();
                }else{
                    yourImageView.setScaleX((float) (x - 0.5));
                    yourImageView.setScaleY((float) (y - 0.5));
                    // Toast.makeText(getApplicationContext(), sil, Toast.LENGTH_SHORT).show();
                }




            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("testintent");
            // replace zabelislightsapp with http:
            String newValue = value.replace("zambelislightsapp:", "http:");
//SILVIA_TOAST            Toast.makeText(getApplicationContext(), newValue, Toast.LENGTH_SHORT).show();

            // Silvia load image from url #1
            ImageView bindImage = (ImageView)findViewById(R.id.ImageView01);
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
            downloadTask.execute(newValue);
        }else {
            // Silvia load image from url #1
            ImageView bindImage = (ImageView)findViewById(R.id.ImageView01);
            String pathToFile = "http://cdn1.iconfinder.com/data/icons/DarkGlass_Reworked/128x128/actions/fileclose.png";
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
            downloadTask.execute(pathToFile);
        }

        //silvia 18.05        //btnCapture = (Button)findViewById(R.id.button12);
//        btnCapture.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
////                camera.takePicture(cameraShutterCallback,
////                        cameraPictureCallbackRaw,
////                        cameraPictureCallbackJpeg);
//                Toast.makeText(getApplicationContext(), "SAVE BUTTON WAS CLICKED", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(intent,0);
//
//
//            }
//        }); });

//        if(getIntent().getData()!=null) {
//
//            Uri data = getIntent().getData();//set a variable for the Intent
//            String scheme = data.toString();//get the scheme (http,https)
//
//            //DownloadImageWithURLTask downloadTask1 = new DownloadImageWithURLTask(bindImage);
//            //downloadTask1.execute(scheme);
//
//            Toast.makeText(getApplicationContext(), "Scheme found!!!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), scheme, Toast.LENGTH_SHORT).show();
//
//            // Silvia load image from url #1
//            ImageView bindImage = (ImageView)findViewById(R.id.ImageView01);
//            String pathToFile = "http://www.mylightstore.co.uk/productImages/M_0931_1.jpg";
//            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
//            downloadTask.execute(scheme);
//
//        }else{
//            String scheme = "noooo Scheme";
//            Toast.makeText(getApplicationContext(), scheme, Toast.LENGTH_SHORT).show();
//
//            // Silvia load image from url #1
//            ImageView bindImage = (ImageView)findViewById(R.id.ImageView01);
//            //String pathToFile = "http://www.mylightstore.co.uk/productImages/M_0931_1.jpg";
//           // DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
//            //downloadTask.execute(pathToFile);
//        }


// Silvia load image from url #1
        ;

        // Intent intent = getIntent();
        // String pathToFile = intent.getDataString().toString();
        // String pathToFile = "http://www.mylightstore.co.uk/productImages/M_0931_1.jpg";
        //  DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
        //  downloadTask.execute(pathToFile);

        // Toast.makeText(getApplicationContext(), pathToFile, Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), pathToFile2, Toast.LENGTH_SHORT).show();



        // draggable image which you want to move
        // findViewById(R.id.imageView).setOnTouchListener(new MyTouchListener());

    }

    @Override
    protected void onResume() {
        super.onResume();

        startBackgroundThread();

        if(mTextureView.isAvailable()) {
            setupCamera(mTextureView.getWidth(), mTextureView.getHeight());
            connectCamera();
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA_PERMISSION_RESULT) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(),
//                        "Application will not run without camera services", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMediaRecorder = new MediaRecorder();
                mIsRecording = true;
                //mRecordImageButton.setImageResource(R.mipmap.btn_video_busy);
                try {
                    createVideoFileName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(this,
//                        "Permission successfully granted!", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this,
//                        "App needs to save video to run", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        closeCamera();

        stopBackgroundThread();

        super.onPause();
    }
    //
//    @Override
//    public void onWindowFocusChanged(boolean hasFocas) {
//        super.onWindowFocusChanged(hasFocas);
//        View decorView = getWindow().getDecorView();
//        if(hasFocas) {
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }
//    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void setupCamera(int width, int height) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for(String cameraId : cameraManager.getCameraIdList()){
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                        CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                int deviceOrientation = getWindowManager().getDefaultDisplay().getRotation();
                mTotalRotation = sensorToDeviceRotation(cameraCharacteristics, deviceOrientation);
                boolean swapRotation = mTotalRotation == 90 || mTotalRotation == 270;
                int rotatedWidth = width;
                int rotatedHeight = height;
                if(swapRotation) {
                    rotatedWidth = height;
                    rotatedHeight = width;
                }
                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotatedWidth, rotatedHeight);
                mVideoSize = chooseOptimalSize(map.getOutputSizes(MediaRecorder.class), rotatedWidth, rotatedHeight);
                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void connectCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
                    cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, mBackgroundHandler);
                } else {
                    if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                        Toast.makeText(this,
//                                "Video app required access to camera", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_RESULT);
                }

            } else {
                cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, mBackgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void startPreview() {
        SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Surface previewSurface = new Surface(surfaceTexture);

        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {
                            try {
                                session.setRepeatingRequest(mCaptureRequestBuilder.build(),
                                        null, mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession session) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Unable to setup camera preview", Toast.LENGTH_SHORT).show();

                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void closeCamera() {
        if(mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void startBackgroundThread() {
        mBackgroundHandlerThread = new HandlerThread("Camera2VideoImage");
        mBackgroundHandlerThread.start();
        mBackgroundHandler = new Handler(mBackgroundHandlerThread.getLooper());
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void stopBackgroundThread() {
        mBackgroundHandlerThread.quitSafely();
        try {
            mBackgroundHandlerThread.join();
            mBackgroundHandlerThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private static int sensorToDeviceRotation(CameraCharacteristics cameraCharacteristics, int deviceOrientation) {
        int sensorOrienatation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        deviceOrientation = ORIENTATIONS.get(deviceOrientation);
        return (sensorOrienatation + deviceOrientation + 360) % 360;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private static Size chooseOptimalSize(Size[] choices, int width, int height) {
        List<Size> bigEnough = new ArrayList<Size>();
        for(Size option : choices) {
            if(option.getHeight() == option.getWidth() * height / width &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        if(bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizeByArea());
        } else {
            return choices[0];
        }
    }

    private void createVideoFolder() {
        File movieFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        mVideoFolder = new File(movieFile, "camera2VideoImage");
        if(!mVideoFolder.exists()) {
            mVideoFolder.mkdirs();
        }
    }

    private File createVideoFileName() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prepend = "VIDEO_" + timestamp + "_";
        File videoFile = File.createTempFile(prepend, ".mp4", mVideoFolder);
        mVideoFileName = videoFile.getAbsolutePath();
        return videoFile;
    }
    private void createImageFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mVideoFolder = new File(imageFile, "camera2Image2");
        if(!mVideoFolder.exists()) {
            mVideoFolder.mkdirs();
        }
    }


    private File createImageFileName() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "camera2Image2");
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private void checkWriteStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                mIsRecording = true;
                //mRecordImageButton.setImageResource(R.mipmap.btn_video_busy);
                try {
                    createVideoFileName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Toast.makeText(this, "app needs to be able to save videos", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
            }
        } else {
            mIsRecording = true;
            //mRecordImageButton.setImageResource(R.mipmap.btn_video_busy);
            try {
                createVideoFileName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //@SuppressWarnings(Deprecated)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void setupMediaRecorder() throws IOException {
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setOutputFile(mVideoFileName);
        mMediaRecorder.setVideoEncodingBitRate(1000000);
        mMediaRecorder.setVideoFrameRate(30);
        if(Build.VERSION.SDK_INT >= 5.0){ mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight()); }
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setOrientationHint(mTotalRotation);
        mMediaRecorder.prepare();
    }



    // This defines your touch listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                return false;
            }
        }
    }
    // Silvia load image from url #2
    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100) {
//            if (resultCode == RESULT_OK) {
//                imageView.setImageURI(file);
//            }
//        }
//    }

}



