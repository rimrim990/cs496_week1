# cs496_week1

### TAB2: Image Gallery

#### Feature
------------
##### [ 기기 갤러리와 연동 ]

기기의 갤러리로부터 이미지를 읽어와
```java
Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
```


갤러리에서 읽어온 이미지를 RecyclerView와 GridLayout을 이용하여 배치

```java
GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
recyclerView.setLayoutManager(gridLayoutManager);
```

##### [ 사진 촬영 ]


기기의 카메라 어플리케이션으로 사진을 촬영하고, 촬영된 사진을 photoURI가 가리키는 위치에 저장

```java
Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
```

이후 MediaScanner를 호출하여 기기의 갤러리를 새로고침

```java
MediaScannerConnection.scanFile(mContext, new String[]{f.toString()}, null, null);
```

##### [ 사진 확대 ]

### TAB3: Alarm

#### Feature
1. 알람 설정
2. 알람 재설정
------------
##### [ 알람 설정 ]

GSON 라이브러리로 알람 list를 JSON 형태로 변환하여 SharedPreference에 저장 

해당 정보는 key와 함께 어플리케이션에 파일 형태로 저장되며 어플리케이션이 삭제되기 전까지 보존된다

``` java
SharedPreferences prefs = getPreferences(context);
SharedPreferences.Editor editor = prefs.edit();

Gson gson = new Gson();
String json = gson.toJson(alarms);

editor.putString(key, json);
editor.commit();
```


정해진 시간이 되면 AlarmManger가 지정된 Broadcast Receiver에게 Broadcast

```java  
AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
Intent intent = new Intent(context, AlarmReceiver.class);

alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent);
```

##### [ 알람 재설정 ]


