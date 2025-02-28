# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.22.1)

# Define project as both C and ASM for cases where assembly is enabled
project("galoislibrary-gradle" C ASM)

# Set wolfCrypt JNI location and wolfSSL directory
set(galoislibrary_DIR ${CMAKE_SOURCE_DIR}/jni/)
set(wolfssl_DIR      ${CMAKE_SOURCE_DIR}/wolfssl/)

# Add header directories to include paths
include_directories(
        ${wolfssl_DIR}
        ${wolfssl_DIR}/openssl
        ${wolfssl_DIR}/wolfcrypt
        ${galoislibrary_DIR}/include
)

# Add preprocessor defines for wolfSSL features
add_definitions(
        -DWOLFSSL_AESGCM
        -DWOLFSSL_AES
        -DHAVE_AESGCM
        -DHAVE_HKDF
        -DHAVE_ECC
        -DHAVE_HMAC
        -DHAVE_HASHDRBG
        -DUSE_FAST_MATH
        -DWOLFSSL_SHA224
        -DWOLFSSL_SHA384
        -DWOLFSSL_SHA512
        -DHAVE_POLY1305
        -DHAVE_CHACHA
        -DDEBUG_WOLFSSL
        -DWOLFSSL_ANDROID_DEBUG
        -DGCM_TABLE_4BIT
)

# Add wolfSSL and wolfCrypt source files
aux_source_directory(${wolfssl_DIR}/src TLS_SOURCES)
list(REMOVE_ITEM TLS_SOURCES ${wolfssl_DIR}/src/bio.c)
list(REMOVE_ITEM TLS_SOURCES ${wolfssl_DIR}/src/conf.c)

aux_source_directory(${wolfssl_DIR}/wolfcrypt/src CRYPTO_SOURCES)
list(REMOVE_ITEM CRYPTO_SOURCES ${wolfssl_DIR}/wolfcrypt/src/evp.c)
list(REMOVE_ITEM CRYPTO_SOURCES ${wolfssl_DIR}/wolfcrypt/src/misc.c)

# Ensure HMAC and KDF are included
list(APPEND CRYPTO_SOURCES
        ${wolfssl_DIR}/wolfcrypt/src/hmac.c
        ${wolfssl_DIR}/wolfcrypt/src/kdf.c
)

# Compile wolfSSL as a STATIC library for better compatibility on Android
add_library(wolfssl STATIC
        ${CRYPTO_SOURCES}
        ${TLS_SOURCES}
)

# Compile wolfCrypt JNI as a SHARED library
add_library(galoislibrary SHARED
        ${galoislibrary_DIR}/galois-lib.c
)

# Find the android log library
find_library(lib-log log)

# Link libwolfssl to the android log library
target_link_libraries(
        wolfssl
        ${lib-log}
)

# Link libwolfcryptjni to libwolfssl
target_link_libraries(
        galoislibrary
        wolfssl
)

# Debug mode, remove for production
set(CMAKE_BUILD_TYPE Debug)
