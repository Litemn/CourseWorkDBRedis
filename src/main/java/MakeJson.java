import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;

public class MakeJson {
    private static final String[] bodys = "Седан Хэтчбек Универсал Купе Кабриолет Родстер Лимузин Пикап Хардтоп Фастбек Минивэн".split(" ");
    private static final String[] brand_names = "Acura Daihatsu Datsun Honda Infiniti Lexus Mazda Mitsubishi Nissan Scion Subaru Suzuki Toyota Buick Cadillac Chevrolet Chrysler Dodge Ford GMC Hummer Jeep Lincoln Mercury Oldsmobile Pontiac Tesla Lada ВАЗ ГАЗ Москвич ТагАЗ УАЗ Audi BMW Mercedes-Benz Opel Porsche Volkswagen Daewoo Hyundai Kia SsangYong Alfa_Romeo Aston_Martin Bentley Bugatti Citroen Ferrari Fiat Jaguar Lamborghini Lancia Land_Rover Maserati Maybach Mini Peugeot Renault Rolls-Royce Rover Saab SEAT Skoda Volvo Brilliance BYD Changan Chery DongFeng FAW Geely Haima Haval JAC Lifan Luxgen".split(" ");
    private static final String[] model_names = "C 2500SS,1600GT JUNIOR,1750 BERLINA,8C-2300,1900/1900TI/1900 Super/1900 Super Ti,1750 SALOON,DUETTO,6C-2300 TURISMO/GT/PESCARA,1900 SPRINT/SUPER SPRINT,1750 SPIDER VELOCE,MONTREAL,6C-2300B TURISMO/GT (1936-39),GIULIETTA SPRINT/SPRINTVELOCE/GIULIA SPRINT/1300 SPRINT,1750 GTV,2000 BERLINA,6C-2300B PESCARA/MILLE MIGLIA,GIULIETTA BERLINA/BERLINA TI,ALFETTA SALOON,8C-2900 A/B SHORT/B LONG,2000,ALFETTA GT,6C-2500 TURISMO/SPORT,GIULIETTA SS/SZ/SZ2/GIULIA SS,ALFETTA 1.6 GT,6C-2500 SUPER SPORT,GIULIETTA SPIDER/SPIDER VELOCE,ALFETTA 2.0 GTV/STRADA,2600 BERLINA,GIULETTA 1/6/1.8 SALOON,2600 SPRINT,MONTREAL,2600 SPIDER,ALFASUD,GIULIA TI/SUPER,6C-1750 TURISMO (17/75 HP),ALFASUD TI,GIULIA 1300TI,GIULIA GT/SPRINT GT/VELOCE/GTC,6C-1750 SPORT/GT (17/85 HP),ALFASUD SPRINT,1300 GT JUNIOR,GIULIA SPIDER/SPIDER VELOCE,6C-1750 GTC,1300 SPIDER JUNIOR,GT 1300 JUNIOR/GT JUNIOR 1.6,6C-1750 SUPER SPORT/GRAN SPORT,GIULIA SUPER,GIULIA 1300/1300TI/1300 SUPER,6C-1900,J1/K1,L,M1,P1,V8/V12,J2/J2X,K2,M2X,K3,P2 MONTE CARLO,SAFARI,PALM BEACH,1½-LITRE/2-LITRE,9 HP,TB14,SPEED 20 SB/SC/SD,FIREFLY 12,TA21/TC21,CRESTED EAGLE,AVLIS TB21,SILVER EAGLE 16 SF/SG,TC21/100 GREY LADY,FIREBIRD,TC108G/TD21,TE/TF21,3½-LITRE SA,SILVER CREST 17-TF/20-TH,SPEED 25 SB/SC,4.3-LITRE,4.3-LITRE SHORT,12/70 SB/SC,12/50 TJ,12/60 TK/TL,12/75 FA/FB/FD/FE,SILVER EAGLE TA/TB/SA/SD/SE,SILVER EAGLE 20 TB/TC,TA14,SPEED 20 SA,AMERICAN MOTORS AMX,AMERICAN MOTORS REBEL,AMERICAN MOTORS MATADOR,AMERICAN MOTORS GREMLIN,AMERICAN MOTORS PACER,AMERICAN MOTORS JEEP WAGONEER,AMERICAN MOTORS JEEP CHEROKEE,AMERICAN MOTORS JAVELIN,AMERICAN MOTORS AMBASSADOR,AMERICAN MOTORS HORNET,C8/CS8,COMPOUND (HOTCHKISS- TEN),V6,T22,SS,ARMSTRONG-SIDDELEY 12/6 SPORTS,ARMSTRONG-SIDDELEY 15/6,ARMSTRONG-SIDDELEY SHORT 20,ARMSTRONG-SIDDELEY SPECIAL 20/LONG 20,ARMSTRONG-SIDDELEY 30 HP MK II,ARMSTRONG-SIDDELEY SPECIAL MK I,ARMSTRONG-SIDDELEY SPECIAL MK II,LANCASTER 16/18HP,ARMSTRONG-SIDDELEY SHORT 17,HURRICANE 16/18 HP,ARMSTRONG-SIDDELEY STANDARD/LONG 17,WHITLEY 18 HP,ARMSTRONG-SIDDELEY 12 PLUS/FOURTEEN,SAPPHIRE 3.4-LITRE/346,ARMSTRONG-SIDDELEY 20/25,SAPPHIRE LIMOUSINE,ARMSTRONG-SIDDELEY 16 HP,SAPPHIRE 234/236,STAR SAPPHIRE,ARMSTRONG-SIDDELEY 12/6,17-50/23-70,LAGONDA (1976-1989),DB2/4 MK 1,LE MANS,DB2/4 MK II,12/50 STANDARD,DB MK III,MK II SHORT,DB4 DROPHEAD COUPE,MK II LONG,DB5,ULSTER,DB6,2-LITRE SPEED MODEL,VOLANTE,15/98 SHORT,15/98 LONG,DB6 MKII,2-LITRE C-TYPE,DB6 VOLANTE,DBS/VANTAGE,DBS V8 SALOON,VANTAGE,VOLANTE,V8 SALOON,TWO-LITRE SPORTS,INTERNATIONAL,LAGONDA,DB2,INTERNATIONAL LE MANS,1½-LITRE/2-LITRE,4.3-LITRE,851S/852S,8-98/8-100,8-125,12-160,6-52/8-50,653/654/851/852,75 VARIANT,SUPER 90,100,100 COUPÈS,'NEW' 100,'NEW' 100 (1976-82),100 AVANT,60/70/75/80/SUPER 90,80,100S COUPE,'NEW' 80,3-LITRE,20 HP SHORT,3-LITRE,A90 ATLANTIC CONVERTIBLE,BIG 7,MAXI 1500,SEVEN,20 HP WHITEHALL,ALLEGRO 1500/1750,A90 ATLANTIC SALOON,18,SEVEN (1930-32),20 HP (1935-38),ALLEGRO 1100/1300,A40 SPORTS,28,SEVEN SWALLOW,LIGHT 12/6, 13.9/15.9 HP,18-22 SERIES,A70 HEREFORD,8,100-SIX,SEVEN ULSTER,LIGHT 12/6 SPORTS, 13.9/15.9 HP,SPRITE,A30,3000 Mk I,SEVEN (LONG),LIGHT 12/6, 13.9/15.9 HP (1935-37),A40 SOMERSET,3000 Mk II,SEVEN 65/NIPPY,10/4,A40/A50/A55 CAMBRIDGE,3000 Mk III,SEVEN SPEEDY,10/4 SPORTS,A90/A95/A105 WESTMINSTER,SPRITE I,SEVEN RUBY,10/4 (1935-36),A35 SALOON,SPRITE II,SEVEN RUBY (1937-39),8HP,10/4 (1937-39),A35 COUNTRYMAN,SPRITE III,TWELVE,1100/1300 RANGE,10HP,10/4 (1939-40),PRINCESS IV,SPRITE IV,HEAVY TWELVE-FOUR,1300GT,12/16HP,LIGHT 12/4,A40 FARINA MKI/II,SPRITE IV/ SPRITE,SIXTEEN,MAXI 1500/1750,A40 DORSET/DEVON,LIGHT 12/4 (1935-36),A55-/A60 CAMBRIDGE,SIXTEEN/EIGHTEEN,MAXI 1750HL/HLS,A125 SHEERLINE,LIGHT 12/4 (1937-39),A99/A110 WESTMINSTER,SIXTEEN/EIGHTEEN (1935-1937),1800 MKII/S,A135 PRINCESS Mk I/Mk III,12,1100/1300,1800,20 HP LONG,2200,A70 HAMPSHIRE,14/6,100,100S,SPRITE MK IV,ADR/ADR SPORT,ADR8 ALPINE EIGHT,ADR 6 BERMEISTER,24 HP,RH3,60,V-8,BEAGLE ESTATE,4-LITRE,S1,3½-LITRE,CONTINENTAL S1,4½-LITRE (1936-39),S2,4½-LITRE MK V,CONTINENTAL S2,S3,CONTINENTAL S3,T1,T-SERIES,COACHBUILT T-SERIES,CORNICHE,T2-SERIES,4½-LITRE (1927-31),4½-LITRE SUPERCHARGED,6½-LITRE STANDARD,MK VI,SPEED SIX,R-TYPE,8-LITRE,CONTINENTAL R,,SPORTS SA322/SPORTS SE328/B65,SPORTS SE492/FOURSOME,B95/B105,T60/T60-4,22/90,S5,S8,S9,335,600,2000,700,2000TI/2000tii,2000C/2000CS,1500/1600/1800,2800CS,1502/1600-2/1600/1602,3.0CS/CSi,1600 CABRIOLET,3.0CSL,2002/2002 Tii/2002 TURBO,3.0S/3.0Si/3.3L,2500/2800,5-SERIES (four cylinder),5-SERIES (six cylinder),315,5-SERIES (520 'six'),319,3-SERIES (four cylinder),315/1, 319/1,3-SERIES (six cylinder),320/321,501,1600/1602/1502,6-SERIES COUPES,326,501 V8/502,2002/2002 TOURING/2002 CABRIOLET,7-SERIES SALOONS,327/80, 327/55,503,2002tii,M1,328,507,2002 TURBO,329,ISETTA,1800,EQUIPE 2-LITRE,MINICAR,EQUIPE GT4/GT4S,875,BUG,EQUIPE 1300GT,HANSA 1800/1800D,ISABELLA/ISABELLA TS,ISABELLA COUPE/CABRIOLET,2.3 SALOON,401,402,412,403,603,404,405 SALOON,405 DROPHEAD COUPE,406,406 ZAGATO,407,408,409,410,411,400,GT,12/55 HP S4C,12/70 PH S4C,14 HP S4D,20/90 HP,4-LITRE,3½-LITRE,XII,SCOUT SERIES 6,THREE-WHEELER TWIN,FW32,THREE-WHEELER 4-CYLINDER,T9,10 HP,LIGHT SIX,SCOUT 9 HP,SCOUT 10 HP SERIES 3/4/5,VARIOUS,TYPE 35,TYPE 40/40A,TYPE 41 ROYALE,TYPE 43/43A,TYPE 44,TYPE 46/46S,TYPE 49,TYPE 50,TYPE 51/51A,TYPE 55,TYPE 57,TYPE 57C,TYPE 57S/57SC,8-50,8-90 MASTER,8-50 (1932),RIVIERA (1965-70),8-50 (1933),RIVIERA (1970-73),8-90 (1933),RIVIERA (1973-76),NA 8/50,RIVIERA (1976-78),NA 8/60,SKYLARK,NA 8/90,ELECTRA,SERIES-40 DA,GRAN SPORT,CENTURY SERIES 60 DA/CO/MO/RO,CENTURY,ROADMASTER/EMPIRE SERIES 80 DA/CO/MO/RO,REGAL,SERIES 90 LIMITED DA/CO/MO/RO,SERIES 40 SPECIAL CO/MO/RO,EMPIRE SERIES 40,STREAMLINE,SEVILLE (1979-85),370/370A,FLEETWOOD BROUGHAM (1970-76),355D/SERIES 10,FLEETWOOD BROUGHAM (1976-84),60,60/65,75,85,90,60 SPECIAL,ELDORADO (1966-70),ELDORADO (1970-78),ELDORADO (1978-date),353/355,SEVILLE (1975-79),452/452A,SUPER SEVEN S4,SUPER SEVEN S3,14CV T8,CORVETTE STING RAY,CAMARO,BLAZER,MONTE CARLO,IMPALA,AD UNIVERSAL,DA MASTER,CAPRICE,EC STANDARD,MONZA,EA MASTER/FC STANDARD/FA MASTER,MASTER GA/GB, HA/HB,MASTER JA/JB,Colt,GALANT,CELESTE,SIGMA,SAPPORO,MIRAGE,LANCER,Connaught,Cord,L29,810/812,812S,Costin".split(" ");
    private static final String[] types = "бензиновый дизельный газовый гибридный роторно-поршневой электрический".split(" ");
    private static final String[] colors = "фиалковый,черный,голубой,серый,зеленый,темно-бордовый,темно-синий,оливковый,фиолетовый,красный,серебряный,серо-зеленый,белый,желтый".split(",");

    public static void main(String[] arg) {
        Random gen = new Random();
        int mileage, price;
        String brand, model, engine, body, pts, color, photo;
        Calendar date;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("cars2.json");
            writer.println("[");
            for (int i = 0; i < 10; i++) {
                mileage = gen.nextInt(999999);
                color = colors[mileage % colors.length];
                pts = (mileage % 90 + 10) + " " + color.charAt(0) + color.charAt(2) + " " + mileage;
                body = bodys[mileage % bodys.length];
                price = mileage % 151 * 10000;
                brand = brand_names[mileage % brand_names.length];
                model = model_names[mileage % model_names.length];
                engine = types[mileage % types.length];
                date = Calendar.getInstance();
                date.set(1950 + mileage % 65, mileage % 12, mileage % 29);
                photo = getURL(String.valueOf(mileage));

                JsonObject object = new JsonObject();
                object.addProperty("id_car", i);
                object.addProperty("brand", brand);
                object.addProperty("model", model);
                object.addProperty("engine", engine);
                object.addProperty("bodyType", body);
                object.addProperty("date_release", (int) date.getTimeInMillis() / 1000);
                object.addProperty("mileage", mileage);
                object.addProperty("pts", pts);
                object.addProperty("color", color);
                object.addProperty("price", price);
                object.addProperty("photo_id", photo);
                writer.println(object.toString());
            }
            writer.println("]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public static String getURL(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            sb.append("www.goo.gl\\");
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
