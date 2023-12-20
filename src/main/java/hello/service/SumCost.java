package hello.service;


import java.util.ArrayList;
import java.util.StringTokenizer;


public class SumCost {
    String input;

    public SumCost (String input_string) {
        this.input = input_string;
    }

    public Integer getCost() {
        StringTokenizer st = new StringTokenizer(input);
        ArrayList<String> sum = new ArrayList<String>();

        //숫자.숫자(원) 찾아서 sum 리스트에 저장
        while (st.hasMoreTokens()) {
            String k = st.nextToken();
            if (k.matches("^\\d*+\\.\\d{3}") || k.matches("^\\d*+\\,\\d{3}")
                    || k.matches("^\\d*+\\.\\d{3}원") || k.matches("^\\d*+\\,\\d{3}원")) {
                sum.add(k);

            }
        }

        //sum 중에서 최댓값 찾기, max에 저장
        int max = 00;
        for (int i=0; i<sum.size(); i++) {
            String temp = (String) sum.get(i);
            temp = temp.replaceAll("[^0-9]", "");
            if (Integer.parseInt(temp) > max) {
                max = Integer.parseInt(temp);
            }
        }

        return max;
    }

    public boolean cash() {
        StringTokenizer st = new StringTokenizer(input);
        ArrayList<String> sum = new ArrayList<String>();


        while (st.hasMoreTokens()) {
            String k = st.nextToken();
            if (k.contains("현금")) {
                return true;
            }
        }

        return false;
    }

    //날짜 string으로 출력, 없는 경우 00/00/00 출력
    public String getDay() {
        StringTokenizer st = new StringTokenizer(input);
        String day = "00/00/00";

        //숫자/숫자(2)/숫자(2)
        while (st.hasMoreTokens()) {
            String k = st.nextToken();
            System.out.println(k);
            if (k.matches("^\\d*+\\/\\d{2}\\/\\d{2}") || k.matches("^\\d*+\\-\\d{2}\\-\\d{2}")
                    || k.matches("^\\d*+년\\d{2}월\\d{2}일")) {
                day = k;
            }
        }
        // 2023/03/09라면 23/03/09로 정규화
        if (day.length() == 10) {
            day = day.substring(2);
        }

        return day;
    }

    public String getCall() {
        StringTokenizer st = new StringTokenizer(input);
        String call = "000-000-0000";

        //숫자-숫자-숫자(4)
        while (st.hasMoreTokens()) {
            String k = st.nextToken();
            if (k.matches("TEL:\\d+\\-\\d{3,4}\\-\\d{4}") || k.matches("^\\d+\\-\\d{3,4}\\-\\d{4}")
                    || k.matches("TEL:\\d+[)]\\d{3,4}\\-\\d{4}") || k.matches("전화번호:\\d+\\-\\d{3,4}\\-\\d{4}")
                    || k.matches("^\\d+[)]\\d{3,4}\\-\\d{4}") || k.matches("전화번호:\\d+[+]\\d{3,4}\\-\\d{4}")
                    || k.matches("^\\d{3,4}\\-\\d{4}") ) {
                call = k;
                System.out.println(call + "k");
            }
        }
        System.out.println(call + "k");
        int i;
        //전화번호 앞의 TEL: 제거
        for (i = 0; Character.isDigit(call.charAt(i)); i++) {
            break;
        }
        call = call.substring(i);
        return call;
    }
}
