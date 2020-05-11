

API Zing MP3 | RedT



Code triển khai các api lấy thông tin nhạc của zing mp3 như lấy thông tin: bài hát, link tải, playlist, zingchart...



// Ví dụ lấy 100 bài hát thịnh hành hiện tại



	 Map<String, String> paramChart = new HashMap<String, String>();
	 paramChart.put("type", "song");
	 paramChart.put("time", "-1");
	 paramChart.put("count", "100");

	 String chartRealtime    = Helpers.buildAPIURL(Helpers.URL_DETAIL, paramChart);
	 System.out.println(chartRealtime);




 //Lấy thông tin bài hát, link tải, ảnh bìa,... của bài hát cho trước
 //For example, get song information




	 Map<String, String> soungInfoParam = new HashMap<String, String>();
	 soungInfoParam.put("id", "ZWBWAIDA");

	 String soungInfo = Helpers.buildAPIURL(Helpers.URL_SOUNG_INFO, soungInfoParam);
	 System.out.println(soungInfo);





// Các API khác, 
    // API

	// get Zing Chart
    // URL   /chart-realtime/get-detail
    // param type=song&time=-1&count=100

	// get Soung info
    // URL   /song/get-song-info
    // param id=XXXXXXX

	// get Lyric
    // URL   /song/get-lyric
    // param id=XXXXXXX

	// get Streamings
	// URL   /song/get-streamings
    // param id=XXXXXXX

	// get Playlist
    // URL   /playlist/get-playlist-detail
    // param id=XXXXXXX

	// get search
    // URL   /api/search
    // param type=song&q=??????&start=0&count=20

	// get search multi
    // URL   /search/multi
    // param q=???????


![Code Test](https://raw.githubusercontent.com/toannguyen2/zingmp3_api/master/demo.png)
![API trả về json](https://raw.githubusercontent.com/toannguyen2/zingmp3_api/master/json.png)
