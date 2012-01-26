package com.brightcove.commons.catalog.objects.enumerations;

import java.util.EnumSet;

/**
 * <p>The geography name/code pair to be used in filtering videos.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    geofilters on a Video object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Video">http://support.brightcove.com/en/docs/media-api-objects-reference#Video</a>.
 * </p>
 * <p>The actual codes are taken from the ISO 3166-1-alpha-2 definitions at
 *    <a href="http://www.iso.org/iso/english_country_names_and_code_elements">http://www.iso.org/iso/english_country_names_and_code_elements</a>.
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum GeoFilterCodeEnum {
	// A
	AF("AFGHANISTAN", "af"),
	AX("ÅLAND ISLANDS", "ax"),
	AL("ALBANIA", "al"),
	DZ("ALGERIA", "dz"),
	AS("AMERICAN SAMOA", "as"),
	AD("ANDORRA", "ad"),
	AO("ANGOLA", "ao"),
	AI("ANGUILLA", "ai"),
	AQ("ANTARCTICA", "aq"),
	AG("ANTIGUA AND BARBUDA", "ag"),
	AR("ARGENTINA", "ar"),
	AM("ARMENIA", "am"),
	AW("ARUBA", "aw"),
	AU("AUSTRALIA", "au"),
	AT("AUSTRIA", "at"),
	AZ("AZERBAIJAN", "az"),
	// B
	BS("BAHAMAS", "bs"),
	BH("BAHRAIN", "bh"),
	BD("BANGLADESH", "bd"),
	BB("BARBADOS", "bb"),
	BY("BELARUS", "by"),
	BE("BELGIUM", "be"),
	BZ("BELIZE", "bz"),
	BJ("BENIN", "bj"),
	BM("BERMUDA", "bm"),
	BT("BHUTAN", "bt"),
	BO("BOLIVIA, PLURINATIONAL STATE OF", "bo"),
	BA("BOSNIA AND HERZEGOVINA", "ba"),
	BW("BOTSWANA", "bw"),
	BV("BOUVET ISLAND", "bv"),
	BR("BRAZIL", "br"),
	IO("BRITISH INDIAN OCEAN TERRITORY", "io"),
	BN("BRUNEI DARUSSALAM", "bn"),
	BG("BULGARIA", "bg"),
	BF("BURKINA FASO", "bf"),
	BI("BURUNDI", "bi"),
	// C
	KH("CAMBODIA", "kh"),
	CM("CAMEROON", "cm"),
	CA("CANADA", "ca"),
	CV("CAPE VERDE", "cv"),
	KY("CAYMAN ISLANDS", "ky"),
	CF("CENTRAL AFRICAN REPUBLIC", "cf"),
	TD("CHAD", "td"),
	CL("CHILE", "cl"),
	CN("CHINA", "cn"),
	CX("CHRISTMAS ISLAND", "cx"),
	CC("COCOS (KEELING) ISLANDS", "cc"),
	CO("COLOMBIA", "co"),
	KM("COMOROS", "km"),
	CG("CONGO", "cg"),
	CD("CONGO, THE DEMOCRATIC REPUBLIC OF THE", "cd"),
	CK("COOK ISLANDS", "ck"),
	CR("COSTA RICA", "cr"),
	CI("CÔTE D'IVOIRE", "ci"),
	HR("CROATIA", "hr"),
	CU("CUBA", "cu"),
	CY("CYPRUS", "cy"),
	CZ("CZECH REPUBLIC", "cz"),
	// D
	DK("DENMARK", "dk"),
	DJ("DJIBOUTI", "dj"),
	DM("DOMINICA", "dm"),
	DO("DOMINICAN REPUBLIC", "do"),
	// E
	EC("ECUADOR", "ec"),
	EG("EGYPT", "eg"),
	SV("EL SALVADOR", "sv"),
	GQ("EQUATORIAL GUINEA", "gq"),
	ER("ERITREA", "er"),
	EE("ESTONIA", "ee"),
	ET("ETHIOPIA", "et"),
	// F
	FK("FALKLAND ISLANDS (MALVINAS)", "fk"),
	FO("FAROE ISLANDS", "fo"),
	FJ("FIJI", "fj"),
	FI("FINLAND", "fi"),
	FR("FRANCE", "fr"),
	GF("FRENCH GUIANA", "gf"),
	PF("FRENCH POLYNESIA", "pf"),
	TF("FRENCH SOUTHERN TERRITORIES", "tf"),
	// G
	GA("GABON", "ga"),
	GM("GAMBIA", "gm"),
	GE("GEORGIA", "ge"),
	DE("GERMANY", "de"),
	GH("GHANA", "gh"),
	GI("GIBRALTAR", "gi"),
	GR("GREECE", "gr"),
	GL("GREENLAND", "gl"),
	GD("GRENADA", "gd"),
	GP("GUADELOUPE", "gp"),
	GU("GUAM", "gu"),
	GT("GUATEMALA", "gt"),
	// Not supported by brightcove media api - GG("GUERNSEY", "gg"),
	GN("GUINEA", "gn"),
	GW("GUINEA-BISSAU", "gw"),
	GY("GUYANA", "gy"),
	// H
	HT("HAITI", "ht"),
	HM("HEARD ISLAND AND MCDONALD ISLANDS", "hm"),
	VA("HOLY SEE (VATICAN CITY STATE)", "va"),
	HN("HONDURAS", "hn"),
	HK("HONG KONG", "hk"),
	HU("HUNGARY", "hu"),
	// I
	IS("ICELAND", "is"),
	IN("INDIA", "in"),
	ID("INDONESIA", "id"),
	IR("IRAN, ISLAMIC REPUBLIC OF", "ir"),
	IQ("IRAQ", "iq"),
	IE("IRELAND", "ie"),
	// Not supported by brightcove media api - IM("ISLE OF MAN", "im"),
	IL("ISRAEL", "il"),
	IT("ITALY", "it"),
	// J
	JM("JAMAICA", "jm"),
	JP("JAPAN", "jp"),
	// not supported by brightcove media api - JE("JERSEY", "je"),
	JO("JORDAN", "jo"),
	// K
	KZ("KAZAKHSTAN", "kz"),
	KE("KENYA", "ke"),
	KI("KIRIBATI", "ki"),
	KP("KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF", "kp"),
	KR("KOREA, REPUBLIC OF", "kr"),
	KW("KUWAIT", "kw"),
	KG("KYRGYZSTAN", "kg"),
	// L
	LA("LAO PEOPLE'S DEMOCRATIC REPUBLIC", "la"),
	LV("LATVIA", "lv"),
	LB("LEBANON", "lb"),
	LS("LESOTHO", "ls"),
	LR("LIBERIA", "lr"),
	LY("LIBYAN ARAB JAMAHIRIYA", "ly"),
	LI("LIECHTENSTEIN", "li"),
	LT("LITHUANIA", "lt"),
	LU("LUXEMBOURG", "lu"),
	// M
	MO("MACAO", "mo"),
	MK("MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF", "mk"),
	MG("MADAGASCAR", "mg"),
	MW("MALAWI", "mw"),
	MY("MALAYSIA", "my"),
	MV("MALDIVES", "mv"),
	ML("MALI", "ml"),
	MT("MALTA", "mt"),
	MH("MARSHALL ISLANDS", "mh"),
	MQ("MARTINIQUE", "mq"),
	MR("MAURITANIA", "mr"),
	MU("MAURITIUS", "mu"),
	YT("MAYOTTE", "yt"),
	MX("MEXICO", "mx"),
	FM("MICRONESIA, FEDERATED STATES OF", "fm"),
	MD("MOLDOVA, REPUBLIC OF", "md"),
	MC("MONACO", "mc"),
	MN("MONGOLIA", "mn"),
	ME("MONTENEGRO", "me"),
	MS("MONTSERRAT", "ms"),
	MA("MOROCCO", "ma"),
	MZ("MOZAMBIQUE", "mz"),
	MM("MYANMAR", "mm"),
	// N
	NA("NAMIBIA", "na"),
	NR("NAURU", "nr"),
	NP("NEPAL", "np"),
	NL("NETHERLANDS", "nl"),
	AN("NETHERLANDS ANTILLES", "an"),
	NC("NEW CALEDONIA", "nc"),
	NZ("NEW ZEALAND", "nz"),
	NI("NICARAGUA", "ni"),
	NE("NIGER", "ne"),
	NG("NIGERIA", "ng"),
	NU("NIUE", "nu"),
	NF("NORFOLK ISLAND", "nf"),
	MP("NORTHERN MARIANA ISLANDS", "mp"),
	NO("NORWAY", "no"),
	// O
	OM("OMAN", "om"),
	// P
	PK("PAKISTAN", "pk"),
	PW("PALAU", "pw"),
	PS("PALESTINIAN TERRITORY, OCCUPIED", "ps"),
	PA("PANAMA", "pa"),
	PG("PAPUA NEW GUINEA", "pg"),
	PY("PARAGUAY", "py"),
	PE("PERU", "pe"),
	PH("PHILIPPINES", "ph"),
	PN("PITCAIRN", "pn"),
	PL("POLAND", "pl"),
	PT("PORTUGAL", "pt"),
	PR("PUERTO RICO", "pr"),
	// Q
	QA("QATAR", "qa"),
	// R
	RE("RÉUNION", "re"),
	RO("ROMANIA", "ro"),
	RU("RUSSIAN FEDERATION", "ru"),
	RW("RWANDA", "rw"),
	// S
	BL("SAINT BARTHÉLEMY", "bl"),
	SH("SAINT HELENA", "sh"),
	KN("SAINT KITTS AND NEVIS", "kn"),
	LC("SAINT LUCIA", "lc"),
	MF("SAINT MARTIN", "mf"),
	PM("SAINT PIERRE AND MIQUELON", "pm"),
	VC("SAINT VINCENT AND THE GRENADINES", "vc"),
	WS("SAMOA", "ws"),
	SM("SAN MARINO", "sm"),
	ST("SAO TOME AND PRINCIPE", "st"),
	SA("SAUDI ARABIA", "sa"),
	SN("SENEGAL", "sn"),
	RS("SERBIA", "rs"),
	SC("SEYCHELLES", "sc"),
	SL("SIERRA LEONE", "sl"),
	SG("SINGAPORE", "sg"),
	SK("SLOVAKIA", "sk"),
	SI("SLOVENIA", "si"),
	SB("SOLOMON ISLANDS", "sb"),
	SO("SOMALIA", "so"),
	ZA("SOUTH AFRICA", "za"),
	GS("SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS", "gs"),
	ES("SPAIN", "es"),
	LK("SRI LANKA", "lk"),
	SD("SUDAN", "sd"),
	SR("SURINAME", "sr"),
	SJ("SVALBARD AND JAN MAYEN", "sj"),
	SZ("SWAZILAND", "sz"),
	SE("SWEDEN", "se"),
	CH("SWITZERLAND", "ch"),
	SY("SYRIAN ARAB REPUBLIC", "sy"),
	// T
	TW("TAIWAN, PROVINCE OF CHINA", "tw"),
	TJ("TAJIKISTAN", "tj"),
	TZ("TANZANIA, UNITED REPUBLIC OF", "tz"),
	TH("THAILAND", "th"),
	TL("TIMOR-LESTE", "tl"),
	TG("TOGO", "tg"),
	TK("TOKELAU", "tk"),
	TO("TONGA", "to"),
	TT("TRINIDAD AND TOBAGO", "tt"),
	TN("TUNISIA", "tn"),
	TR("TURKEY", "tr"),
	TM("TURKMENISTAN", "tm"),
	TC("TURKS AND CAICOS ISLANDS", "tc"),
	TV("TUVALU", "tv"),
	// U
	UG("UGANDA", "ug"),
	UA("UKRAINE", "ua"),
	AE("UNITED ARAB EMIRATES", "ae"),
	GB("UNITED KINGDOM", "gb"),
	US("UNITED STATES", "us"),
	UM("UNITED STATES MINOR OUTLYING ISLANDS", "um"),
	UY("URUGUAY", "uy"),
	UZ("UZBEKISTAN", "uz"),
	// V
	VU("VANUATU", "vu"),
	// EE("VATICAN CITY STATE", "see HOLY SEE"),
	VE("VENEZUELA, BOLIVARIAN REPUBLIC OF", "ve"),
	VN("VIET NAM", "vn"),
	VG("VIRGIN ISLANDS, BRITISH", "vg"),
	VI("VIRGIN ISLANDS, U.S.", "vi"),
	// W
	WF("WALLIS AND FUTUNA", "wf"),
	EH("WESTERN SAHARA", "eh"),
	// Y
	YE("YEMEN", "ye"),
	// Z
	ZM("ZAMBIA", "zm"),
	ZW("ZIMBABWE", "zw");	
	
	private final String name;
	private final String code;
	GeoFilterCodeEnum(String name, String code){
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	
	public static GeoFilterCodeEnum lookupByName(String name){
		if(name == null){
			return null;
		}
		
		String upperName = name.toUpperCase();
		for(GeoFilterCodeEnum code : EnumSet.allOf(GeoFilterCodeEnum.class)){
			if(code.name.toUpperCase().equals(upperName)){
				return code;
			}
		}
		return null;
	}
	
	public static GeoFilterCodeEnum lookupByCode(String code){
		if(code == null){
			return null;
		}
		
		String upperCode = code.toUpperCase();
		for(GeoFilterCodeEnum lookup : EnumSet.allOf(GeoFilterCodeEnum.class)){
			if(lookup.code.toUpperCase().equals(upperCode)){
				return lookup;
			}
		}
		return null;
	}
}
