package com.benedetto.core.utils;

public final class SecureStorage {
//TODO: Add WolfCrypt C Lib. Android crypto sucks
    /*
    private final MasterKey masterKey;
    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor editor;

    private SecureStorage(Context context) {
        masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences.create(context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        // use the shared preferences and editor as you normally would
        editor = sharedPreferences.edit();
    }


    public void saveSecureData(String key, String value) {
        sharedPreferences.edit().putString(key, value);
    }

    //public void getSecureData(key:String):String?=prefs.getString(key,null)

     */
}











