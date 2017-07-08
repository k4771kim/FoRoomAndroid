/*
 * Copyright 2014 Google Inc. All Rights Reserved.

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.forroom.suhyemin.kimbogyun.songmin;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;

import com.forroom.suhyemin.kimbogyun.songmin.vrHelperPackage.MatrixHelper;
import com.forroom.suhyemin.kimbogyun.songmin.vrHelperPackage.Sphere;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;

public class
        vrActivity extends CardboardActivity implements CardboardView.StereoRenderer {

    private static final String TAG = "MainActivity";
    private String filepath;
    private Sphere mSphere;
    private final float[] mCamera = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewProjectionMatrix = new float[16];

    private float CAMERA_Z = 0.5f;
    private float[] mView = new float[16];
    private CardboardView mCardboardView;
    private int mCurrentPhotoPos = 0;
    private boolean mIsCardboardTriggered;


    /**
     * Checks if we've had an error inside of OpenGL ES, and if so what that error is.
     *
     * @param label Label to report in case of error.
     */
    private static void checkGLError(String label) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, label + ": glError " + error);
            throw new RuntimeException(label + ": glError " + error);
        }
    }

    /**
     * Sets the view to our CardboardView and initializes the transformation matrices we will use
     * to render our scene.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filepath = getIntent().getStringExtra("imgpath");
//        Log.e("filePath", filepath+"");
//        setContentView(R.layout.activity_vr);
//        mCardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        mCardboardView = new CardboardView(vrActivity.this);
        setContentView(mCardboardView);
        mCardboardView.setRenderer(this);
        setCardboardView(mCardboardView);

    }

    @Override
    public void onRendererShutdown() {
        Log.i(TAG, "onRendererShutdown");
    }

    @Override
    public void onSurfaceChanged(int width, int height) {

        /**Setting the view port to the width and height of the device **/
        glViewport(0, 0, width, height);
        /** Setting the projection Matrix for the view **/
        MatrixHelper.perspectiveM(mProjectionMatrix, 90, (float) width
                / (float) height, 1f, 10f);

        Log.i(TAG, "onSurfaceChanged");
    }

    /**
     * Creates the buffers we use to store information about the 3D world.
     * <p/>
     * <p>OpenGL doesn't use Java arrays, but rather needs data in a format it can understand.
     * Hence we use ByteBuffers.
     *
     * @param config The EGL configuration used when creating the surface.
     */
    @Override
    public void onSurfaceCreated(EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated");
        GLES20.glClearColor(1f, 1f, 0f, 1f);// Dark background so text shows up well.

        /** Creating the Sphere for Rendering images inside the sphere **/
        mSphere = new Sphere(this, 50, 5f);
        mSphere.loadTexture(this, filepath);
        checkGLError("onSurfaceCreated");

    }

    /**
     * Prepares OpenGL ES before we draw a frame.
     *
     * @param headTransform The head transformation in the new frame.
     */
    @Override
    public void onNewFrame(HeadTransform headTransform) {

        /** Setting the camera in the center **/
        Matrix.setLookAtM(mCamera, 0, 0.0f, 0.0f, CAMERA_Z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        checkGLError("onReadyToDraw");
    }

    /**
     * Draws a frame for an eye.
     *
     * @param eye The eye to render. Includes all required transformations.
     */
    @Override
    public void onDrawEye(Eye eye) {


        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        /** Camera should move based on the user movement **/
        Matrix.multiplyMM(mView, 0, eye.getEyeView(), 0, mCamera, 0);

        /** setting the view projection matrix **/
        multiplyMM(mViewProjectionMatrix, 0, mProjectionMatrix, 0, mView, 0);

        /** Drawing the sphere  and apply the projection to it**/
        mSphere.draw(mViewProjectionMatrix);

        checkGLError("onDrawEye");

        if (mIsCardboardTriggered) {
            mIsCardboardTriggered = false;
            resetTexture();
            System.gc();
        }


    }

    @Override
    public void onFinishFrame(Viewport viewport) {
    }


    /**
     * Reload the texture
     */
    private void resetTexture() {
        mSphere.deleteCurrentTexture();
        checkGLError("after deleting texture");
        mSphere.loadTexture(this, filepath);
        checkGLError("loading texture");
    }

}
