# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

cmake_minimum_required(VERSION 3.22.1)

# build script scope).
project("circleimagenativeapp")
add_library(${CMAKE_PROJECT_NAME} SHARED native-lib.cpp)

add_library("circle_image" SHARED my_circle-lib.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME} android log)
target_link_libraries("circle_image" android log)