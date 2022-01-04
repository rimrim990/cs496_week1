# cs496_week1

### TAB1: Contacts

#### Feature
------------
##### [ 연락처 앱과 연동하기 ]

ContactsContract을 이용하여 핸드폰 내 연락처 정보를 가져왔다.
이 때 이름과 번호가 중복되는 경우 HashSet을 이용하여 제외시켰다.
```java
if(cursor != null){
    HashSet<Contact> contactHashSet = new HashSet<>();
    while(cursor.moveToNext()){
        if(!contactHashSet.contains(phoneContact)){
            contacts.add(phoneContact);
            contactHashSet.add(phoneContact);}
```

##### [ 연락처 추가 삭제 ]

add 버튼에 설정된 onClick 함수에서 AlertDialog를 사용하여 이름 번호를 
입력받고 arraylist 내 알파벳 순서에 따라 추가했다. 또한 intent을 사용하여
핸드폰 연락처에도 추가하였다.

	
각 view에 삭제 버튼을 만들어 누를때 getAdapterPostition() 을 이용하여 arraylist 상의
위치를 구하고 arraylist와 핸드폰 내 연락처에서 삭제해주었다.
```java
  Contact deletedContact = contacts.remove(getAdapterPosition());
  notifyItemRemoved(getAdapterPosition());
  deleteContact(context, deletedContact.getNumber(), deletedContact.getName());
```


##### [ 전화 걸기 ]

앱 내 연락처 항목을 누르면 새로운 fragment 으로 이동하여
이름, 번호를 보여주는 동시에 전화 버튼이 있어 누르면 바로 전화할 수 있도록 구현했다.

### TAB2: Image Gallery

#### Feature
1. 기기 갤러리와 연동
2. 사진 촬영
3. 사진 삭제
4. 사진 확대
------------
##### [ 기기 갤러리와 연동 ]

기기의 갤러리로부터 이미지 경로를 읽어와 imagePaths 에 저장

```java
// this method will stores all the images
// from the gallery in Cursor
Cursor cursor = mContext.getContentResolver()
                         .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

...

// after that we are getting the image file path
// and adding that path in our array list.
imagePaths.add(cursor.getString(dataColumnIndex));
```


Glide 라이브러리를 이용하여 뷰에 이미지 로드


```java
 Glide.with(context)
                    .load(Uri.fromFile(imgFile))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(((ViewHolderItem)holder).iv_icon);
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

##### [ 사진 삭제 ]
이미지를 길게 클릭하면 삭제된다

```java
holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {

      if(imgFile.exists()) {
        imgFile.delete();
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, 
                                Uri.fromFile(new File(imagePaths.get(holder.getAdapterPosition())))));

        imagePaths.remove(holder.getAdapterPosition());
        ...
        }

        return true;
     }
});
```

##### [ 사진 확대 ]
```java
fullView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // inside on touch event method we are calling on
                // touch event method and passing our motion event to it.
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
```


### TAB3: Alarm

#### Feature
1. 알람 설정
2. 알람 재설정
------------
##### [ 알람 설정 ]

<p align="center">
	<img src="https://user-images.githubusercontent.com/62409503/148044449-09445345-ce75-4a30-9d92-1c4b48beff5a.png" width="200px" />
	<img src="https://user-images.githubusercontent.com/62409503/148045020-95655b3b-4994-4315-8725-79f216c31fd2.png" width="200px" />
</p>												      

scheduleAlarm 버튼을 클릭하면 사용자의 입력 값을 바탕으로 새로운 알람을 생성하고, 생성된 알람들은 adapter에서 list로 관리

```java
scheduleAlarm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ...

                        scheduleAlarm(curHour, curMinute, alarmTitle);
                        alarmRecyclerViewAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
```

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

snooze 버튼을 클릭하면 10분 후에 다시 울린다

```java
snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 5);

                Alarm alarm = new Alarm(
                        ...
                );

                alarm.schedule(getApplicationContext());

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });
```

##### [ 걸어서 알람 해제 ]

SensorEventListener 클래스를 이용하여  
센서를 관리하고 이용할 수 있도록 하는 SensorManager을 등록하였다.
기기내 STEP_DETECTOR 센서를 이용했다.

```java
public class RingActivity  extends AppCompatActivity implements SensorEventListener {
...
  sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
  stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
...

```

onSensorChanged는 등록된 센서값이 변했을 때 호출되는 함수로,
함수가 불릴때마다 전역 변수 currentStep을 1씩 증가스켰다.
currentStep가 5 이상이 될때 "dismiss"와 "snooze" 버튼의 visibililty을 
"gone"에서 "visible"로 바꿔 버튼을 알람을 끌수 있도록 하였다.

```java
@Override
public void onSensorChanged(SensorEvent sensorEvent) {
    ...
        if( ... ){
            currentSteps++;
            String s = "You Walked " + String.valueOf(currentSteps) + " steps!!";
            stepCountView.setText(s);
            if(currentSteps >= 5) {
                dismiss.setVisibility(View.VISIBLE);
                snooze.setVisibility(View.VISIBLE);
    ...
```
