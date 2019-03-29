#include <jni.h>
#include <string>
extern "C"{
 int x_main(int argc,const char * argv[]);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_neteast_1bsdiff_1demo_MainActivity_stringFromJNI(JNIEnv *env, jobject instance,jstring oldApk_,
                                                            jstring patch_,jstring output_){

      const char *oldApk = env ->GetStringUTFChars(oldApk_,0);

      const char *patch = env ->GetStringUTFChars(patch_,0);

      const char *output = env ->GetStringUTFChars(output_,0);

//
      const char * argv[] = {"",oldApk,patch,output};
       x_main(4,argv);

      env->ReleaseStringUTFChars(oldApk_,oldApk);
      env->ReleaseStringUTFChars(patch_,patch);
      env->ReleaseStringUTFChars(output_,output);

}