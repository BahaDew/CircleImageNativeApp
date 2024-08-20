#include<jni.h>
#include<cmath>

using namespace std;


extern "C" JNIEXPORT jintArray
JNICALL Java_com_example_circleimagenativeapp_MainActivity_getCirclePixels(
        JNIEnv *env,
        jobject,
        jintArray pixels,
        int with,
        int height,
        int size) {

    int mx = with / 2;
    int my = height / 2;
    int radius = fmin(my,mx);
    jint *arrJint = env->GetIntArrayElements(pixels, NULL);

    for(int y = 0; y < height; y++) {
        for(int x = 0; x < with; x++) {
            int index = y * with + x;
            if(pow(y - my, 2) + pow(x - mx, 2) > pow(radius, 2)) {
                arrJint[index] = 0;
            }
        }
    }
    env->SetIntArrayRegion(pixels, 0, size, arrJint);

    return pixels;
}