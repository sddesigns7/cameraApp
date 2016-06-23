package mobapptut.com.zambelislightscamapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import zambelislights.com.cameraapp.R;

public class bellowLollipop extends Activity {

    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;

    private FrameLayout cameraPreviewLayout;
    private ImageView capturedImageHolder;

    //silvia button #1
    private ImageButton clickButtonPlusImg;
    private ImageButton clickButtonMinusImg;

    private Button backButton;

    int windowwidth;
    int windowheight;

        // SILVIA 18.05 #1
    private Button btnCapture = null;

    private LinearLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bellow_lollipop);
       // Toast.makeText(getApplicationContext(), "05-bellowLoll", Toast.LENGTH_SHORT).show();
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

        //BEGINING
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

                        layoutParams.leftMargin = x_cord - 25;
                        layoutParams.topMargin = y_cord - 75;

                        img.setLayoutParams(layoutParams);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //END


        cameraPreviewLayout = (FrameLayout)findViewById(R.id.camera_preview);
        capturedImageHolder = (ImageView)findViewById(R.id.captured_image);

        camera = checkDeviceCamera();
        mImageSurfaceView = new ImageSurfaceView(bellowLollipop.this, camera);
        cameraPreviewLayout.addView(mImageSurfaceView);

        //Button captureButton = (Button)findViewById(R.id.button);
//        captureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                camera.takePicture(null, null, pictureCallback);
//                Toast.makeText(getApplicationContext(),"Picture Button clicked!",Toast.LENGTH_SHORT).show();
//            }
//        });

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Button clicked!",Toast.LENGTH_SHORT).show();
                Intent backtoHome = new Intent(bellowLollipop.this, CategoryActivity.class);
                startActivity(backtoHome);
                finish();
//                Bitmap bitmap = takeScreenshot();
//                saveBitmap(bitmap);

            }
        });



        //silvia button

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
                    //Toast.makeText(getApplicationContext(), maxreach, Toast.LENGTH_SHORT).show();
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
                   // Toast.makeText(getApplicationContext(), minreach, Toast.LENGTH_SHORT).show();
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
           // Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//SILVIA_TOAST            Toast.makeText(getApplicationContext(), newValue, Toast.LENGTH_SHORT).show();

            // Silvia load image from url #1
            ImageView bindImage = (ImageView)findViewById(R.id.ImageView01);
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
            downloadTask.execute(newValue);
        }else{
            // Silvia load image from url #1
            ImageView bindImage = (ImageView)findViewById(R.id.ImageView01);
            String pathToFile = "http://cdn1.iconfinder.com/data/icons/DarkGlass_Reworked/128x128/actions/fileclose.png";
            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
            downloadTask.execute(pathToFile);
        }

//        if(getIntent().getData()!=null) {
//
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
////            String pathToFile = "http://www.mylightstore.co.uk/productImages/M_0931_1.jpg";
//            //String pathToFile = "http://cdn1.iconfinder.com/data/icons/DarkGlass_Reworked/128x128/actions/fileclose.png";
//            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
//           // downloadTask.execute(pathToFile);
//
//
//        }


        // draggable image which you want to move
        //findViewById(R.id.ImageView01).setOnTouchListener(new MyTouchListener());


        //silvia 18.05 #2
        //btnCapture = (Button)findViewById(R.id.button12);
//        btnCapture.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                camera.takePicture(cameraShutterCallback,
//                        cameraPictureCallbackRaw,
//                        cameraPictureCallbackJpeg);
//
//            }
//        });
    }
    //silvia 18.05 #3
Camera.ShutterCallback cameraShutterCallback = new Camera.ShutterCallback()
    {
        @Override
        public void onShutter()
        {
            // TODO Auto-generated method stub
        }
    };

    PictureCallback cameraPictureCallbackRaw = new PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            // TODO Auto-generated method stub
        }
    };

    PictureCallback cameraPictureCallbackJpeg = new PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            // TODO Auto-generated method stub
            Bitmap cameraBitmap = BitmapFactory.decodeByteArray
                    (data, 0, data.length);

            int   wid = cameraBitmap.getWidth();
            int  hgt = cameraBitmap.getHeight();

             // Toast.makeText(getApplicationContext(), wid+""+hgt, Toast.LENGTH_SHORT).show();
            Bitmap newImage = Bitmap.createBitmap
                    (wid, hgt, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(newImage);

            canvas.drawBitmap(cameraBitmap, 0f, 0f, null);
//
//            Drawable drawable = getResources().getDrawable
//                    (R.mipmap.ic_launcher);
            ImageView thisisImg= (ImageView)findViewById(R.id.ImageView01);

            float xxx = thisisImg.getX();
            float yyy = thisisImg.getY();
            int int_x = Math.round(xxx);
            int int_y = Math.round(yyy);
            String formattedNumber = Float.toString(yyy);
           // Toast.makeText(getApplicationContext(), formattedNumber, Toast.LENGTH_LONG).show();

            Drawable drawable = thisisImg.getDrawable();
            drawable.setBounds(int_y, int_x, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);



            File storagePath = new File(Environment.
                    getExternalStorageDirectory() + "/Zambelislights/");
            storagePath.mkdirs();

            File myImage = new File(storagePath,
                    Long.toString(System.currentTimeMillis()) + ".jpg");

            try
            {
                FileOutputStream out = new FileOutputStream(myImage);
                newImage.compress(Bitmap.CompressFormat.JPEG, 80, out);


                out.flush();
                out.close();
            }
            catch(FileNotFoundException e)
            {
                Log.d("In Saving File", e + "");
            }
            catch(IOException e)
            {
                Log.d("In Saving File", e + "");
            }

            camera.startPreview();



            newImage.recycle();
            newImage = null;

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);

            intent.setDataAndType(Uri.parse("file://" + myImage.getAbsolutePath()), "image/*");
           // startActivity(intent);

            AlertDialog alertDialog = new AlertDialog.Builder(bellowLollipop.this).create();
            alertDialog.setTitle("Picture Saved!");
            alertDialog.setMessage(" in folder Zambelislights");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

            alertDialog.show();


        }
    };
    //silvia 18.05 #3 ENDS

    private Camera checkDeviceCamera(){
        Camera mCamera = null;
        try {
            mCamera = Camera.open();

           // mCamera.setDisplayOrientation(90);


            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(180);
                //Toast.makeText(getApplicationContext(),"open",Toast.LENGTH_SHORT).show();
            } else {
                camera.setDisplayOrientation(0);
            }

            Camera.Parameters parameters = camera.getParameters();


//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            {
//                parameters.set("orientation", "portrait");
//                parameters.set("rotation",22);
//                Toast.makeText(getApplicationContext(),"portrait",Toast.LENGTH_SHORT).show();
//            }
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//            {
//                parameters.set("orientation", "landscape");
//                parameters.set("rotation", 0);
//                Toast.makeText(getApplicationContext(),"landscape",Toast.LENGTH_SHORT).show();
//            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCamera;
    }

    PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if(bitmap==null){
                //Toast.makeText(bellowLollipop.this, "Captured image is empty", Toast.LENGTH_LONG).show();
                return;
            }else{

            }
            capturedImageHolder.setImageBitmap(scaleDownBitmapImage(bitmap, 300, 200 ));
        }
    };

    private Bitmap scaleDownBitmapImage(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return resizedBitmap;
    }


    // This defines your touch listener
    @TargetApi(11)
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
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
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
}
