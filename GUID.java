package com.angle.http;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 获得随机数
 * 
 * @author dell
 *
 */
public class GUID {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	private static String getRandomGUID(boolean secure) {
		String valueBeforeMD5 = "";
		String valueAfterMD5 = "";
		Random myRand = null;
		SecureRandom mySecureRand = null;
		String s_id = "";
		int PAD_BELOW = 0x10;
		int TWO_BYTES = 0xFF;
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
			System.out.println(s_id);
			md5 = MessageDigest.getInstance("MD5");
			long time = System.currentTimeMillis();
			long rand = secure ? mySecureRand.nextLong() : myRand.nextLong();
			System.out.println(rand);
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));
			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());
			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer(32);
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & TWO_BYTES;
				if (b < PAD_BELOW) {
					sb.append('0');
				}

				sb.append(Integer.toHexString(b));
			}

			valueAfterMD5 = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueAfterMD5;
	}

	/**
	 * 生成36位随机数
	 * 
	 * @return guid
	 */
	public static String getGuid() {
		String guid = getRandomGUID(true).toUpperCase();
		if (!"".equals(guid) && null != guid) {
			guid = guid.substring(0, 8) + "-" + guid.substring(8, 12) + "-" + guid.substring(12, 16) + "-"
					+ guid.substring(16, 20) + "-" + guid.substring(20, 32);
		}
		return guid;
	}

	/**
	 * 生成6位随机数
	 * 
	 * @param secure
	 * @return
	 */
	public static String getSecureRand(boolean secure) {
		SecureRandom mySecureRand = null;
		Random myRand = null;
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		long rand = secure ? mySecureRand.nextLong() : myRand.nextLong();
		String guid = Long.toString(rand).substring(2, 6);
		return guid;
	}

	// 设定几位数

	private static final int LENGTH = 8;

	/**
	 * * 这是典型的随机洗牌算法。
	 * 
	 * * 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域）
	 * 
	 * * 算法时间复杂度O（n）
	 * 
	 * * @return 随机8为不重复数组
	 * 
	 * 
	 */

	public static String generateNumber() {

		String no = "";

		// 初始化备选数组

		int[] defaultNums = new int[10];

		for (int i = 0; i < defaultNums.length; i++) {

			defaultNums[i] = i;

		}

		Random random = new Random();

		int[] nums = new int[LENGTH];

		// 默认数组中可以选择的部分长度

		int canBeUsed = 10;

		// 填充目标数组

		for (int i = 0; i < nums.length; i++) {

			// 将随机选取的数字存入目标数组

			int index = random.nextInt(canBeUsed);

			nums[i] = defaultNums[index];

			// 将已用过的数字扔到备选数组最后，并减小可选区域

			swap(index, canBeUsed - 1, defaultNums);

			canBeUsed--;

		}

		if (nums.length > 0) {

			for (int i = 0; i < nums.length; i++) {

				no += nums[i];

			}

		}

		return no;

	}

	/**
	 * 
	 * 交换方法
	 * 
	 * @param i
	 *            交换位置
	 * 
	 * @param j
	 *            互换的位置
	 * 
	 * @param nums
	 *            数组
	 */

	private static void swap(int i, int j, int[] nums) {

		int temp = nums[i];

		nums[i] = nums[j];

		nums[j] = temp;

	}

	/**
	 * 
	 * 获取8位数
	 * 
	 * @return
	 */

	public static String generateNumber2() {

		String no = "";

		int num[] = new int[8];

		int c = 0;

		for (int i = 0; i < 8; i++) {

			num[i] = new Random().nextInt(10);

			c = num[i];

			for (int j = 0; j < i; j++) {

				if (num[j] == c) {

					i--;

					break;

				}

			}

		}

		if (num.length > 0) {

			for (int i = 0; i < num.length; i++) {

				no += num[i];

			}

		}

		return no;

	}
	
	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 每次生成的len位数都不相同
	 * 
	 * @param param
	 * @return 定长的数字
	 */

	public static int getNotSimple(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}

	/**
	 * 获取8位不重复随机码（取当前时间戳转化为十六进制）
	 * 
	 * @author ShelWee
	 * @param time
	 * @return
	 */
	public static String toHex(long time) {
		return Integer.toHexString((int) time);
	}

	
	/** 
     * 产生随机的2位数 
     * @return 
     */  
    public static String getTwo(){  
        Random rad=new Random();  
  
        String result  = rad.nextInt(100) +"";  
  
        if(result.length()==1){  
            result = "0" + result;  
        }  
        return result;  
    } 
    
    public static int getSecureRand() {
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 9; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 6; i++) {
			result = result * 10 + array[i];
		}
		// System.out.println(result);
		return result;
	}

    /**
	 * MD5 32位加密方法二 小写
	 * 
	 * @param str
	 * @return
	 */

	public final static String get32MD5Str1(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * MD5 32位加密方法二 小写
	 * 
	 * @param str
	 * @return
	 */

	public final static String get32MD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	
	
	
	//获取本机Ip
		@SuppressWarnings("rawtypes")
		public static String localIp() {
			String ip = null;
			Enumeration allNetInterfaces;
			try {
				allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				while (allNetInterfaces.hasMoreElements()) {
					NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
					List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
					for (InterfaceAddress add : InterfaceAddress) {
						InetAddress Ip = add.getAddress();
						if (Ip != null && Ip instanceof Inet4Address) {
							ip = Ip.getHostAddress();
						}
					}
				}
			} catch (SocketException e) {
				// TODO Auto-generated catch block

			}
			return ip;
		}
		
		
		/**
		 * 元转换成分
		 * 
		 * @param money
		 * @return
		 */
		public static String getMoney(String amount) {
			if (amount == null) {
				return "";
			}
			// 金额转化为分为单位
			String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																	// 或者$的金额
			int index = currency.indexOf(".");
			int length = currency.length();
			Long amLong = 0l;
			if (index == -1) {
				amLong = Long.valueOf(currency + "00");
			} else if (length - index >= 3) {
				amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
			} else if (length - index == 2) {
				amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
			} else {
				amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
			}
			return amLong.toString();
		}

		/**
		 * 获取随机字符串
		 * 
		 * @return
		 */
		public static String getNonceStr() {
			// 随机数
			String currTime = TenpayUtil.getCurrTime();
			// 8位日期
			String strTime = currTime.substring(8, currTime.length());
			// 四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";
			// 10位序列号,可以自行调整。
			return strTime + strRandom;
		}

		public static String new_date() {
			SimpleDateFormat str_t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sDate = str_t.format(new Date());
			return sDate;
		}
		
		public static String getShortUuid() {
			StringBuffer stringBuffer = new StringBuffer();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			for (int i = 0; i < 8; i++) {
				String str = uuid.substring(i * 4, i * 4 + 4);
				int strInteger = Integer.parseInt(str, 16);
				stringBuffer.append(chars[strInteger % 0x3E]);
			}

			return stringBuffer.toString();
		}
	
		/**
		 * 检测手机号码是否正确
		 * 
		 * @param telPhone
		 * @return
		 */
		public static boolean checkMobilPhone(String telPhone) {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])| (17[0,1,3,5-8]))\\d{8}$");
	        Matcher m = p.matcher(telPhone);
	        return m.matches();
		}
		public static boolean isNum(String str) {
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*");
			java.util.regex.Matcher match = pattern.matcher(str);
			if (match.matches() == false) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(new GUID().getSecureRand(true));
		//System.out.println(java.util.UUID.randomUUID());
		
//		 List<String> list1 = new ArrayList<String>();
//         List<String> list2 = new ArrayList<String>();
//         //给list1赋值
//         list1.add("测");
//         list1.add("试");
//         list1.add("一");
//         list1.add("下");
//         //给list2赋值
//         list2.add("下");
//         list2.add("下");
//         list2.add("下");
//         list2.add("下");
//         //将list1.list2合并
//         list1.addAll(list2);
//         //循环输出list1 看看结果
//         for (String s : list1) {
//             System.out.print(s);
//         }
		
		    
	       
	          
	      
		
		
	}
}
