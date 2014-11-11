package com.movitech.grande.generic.interfaces;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:17:04 AM
 * @Description: This is David Wu's property.
 *
 **/

public interface IFileHandler {
	public enum CacheDir {
		IMAGE("/.image/"), LOG("/.log/"), AUDIO("/.audio/"), VIDEO("/.video/"), AVATAR(
				"/.avatar/");

		private String dir;

		private CacheDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.valueOf(this.dir);
		}

		public String getDir() {
			return dir;
		}
	}

	public enum DataDir {
		BACKUP("/.backup/"), GIF("/.gif/"), FLAGS("/.flags/"), DATABASE("/.db/"), EMOJI(
				"/.emoji/");

		private String dir;

		private DataDir(String dir) {
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.valueOf(this.dir);
		}

		public String getDir() {
			return dir;
		}
	}

	public String getCacheDirByType(CacheDir dir);

	public String getFileDirByType(DataDir dir);

}
