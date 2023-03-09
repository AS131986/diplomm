package Data;

import lombok.SneakyThrows;
import lombok.Value;

import java.sql.DriverManager;

public class DataHelper {

    public static DataHelper.CardInfoApproved CardInfoApproved;

    public static class CardInfoApproved {
        private String cardNumberApproved;
        private String cardMonthApproved;
        private String cardYearApproved;
        private String cardCvvApproved;

        public CardInfoApproved(String cardNumberApproved, String cardMonthApproved, String cardYearApproved, String cardCvvApproved) {
            this.cardNumberApproved = cardNumberApproved;
            this.cardMonthApproved = cardMonthApproved;
            this.cardYearApproved = cardYearApproved;
            this.cardCvvApproved = cardCvvApproved;
        }

        public String getCardNumberApproved() {
            return cardNumberApproved;
        }

        public void setCardNumberApproved(String cardNumberApproved) {
            this.cardNumberApproved = cardNumberApproved;
        }

        public String getCardMonthApproved() {
            return cardMonthApproved;
        }

        public void setCardMonthApproved(String cardMonthApproved) {
            this.cardMonthApproved = cardMonthApproved;
        }

        public String getCardYearApproved() {
            return cardYearApproved;
        }

        public void setCardYearApproved(String cardYearApproved) {
            this.cardYearApproved = cardYearApproved;
        }

        public String getCardCvvApproved() {
            return cardCvvApproved;
        }

        public void setCardCvvApproved(String cardCvvApproved) {
            this.cardCvvApproved = cardCvvApproved;
        }
    }

    public static CardInfoApproved getCardInfoApproved() {
        return new CardInfoApproved("1111 2222 3333 4444", "11", "25", "111");
    }

    public static class CardInfoDeclined {
        private String cardNumberDeclined;
        private String cardMonthDeclined;
        private String cardYearDeclined;
        private String cardCvvDeclined;

        public String getCardNumberDeclined() {
            return cardNumberDeclined;
        }

        public void setCardNumberDeclined(String cardNumberDeclined) {
            this.cardNumberDeclined = cardNumberDeclined;
        }

        public String getCardMonthDeclined() {
            return cardMonthDeclined;
        }

        public void setCardMonthDeclined(String cardMonthDeclined) {
            this.cardMonthDeclined = cardMonthDeclined;
        }

        public String getCardYearDeclined() {
            return cardYearDeclined;
        }

        public void setCardYearDeclined(String cardYearDeclined) {
            this.cardYearDeclined = cardYearDeclined;
        }

        public String getCardCvvDeclined() {
            return cardCvvDeclined;
        }

        public void setCardCvvDeclined(String cardCvvDeclined) {
            this.cardCvvDeclined = cardCvvDeclined;
        }

        public CardInfoDeclined(String cardNumberDeclined, String cardMonthDeclined, String cardYearDeclined, String cardCvvDeclined) {
            this.cardNumberDeclined = cardNumberDeclined;
            this.cardMonthDeclined = cardMonthDeclined;
            this.cardYearDeclined = cardYearDeclined;
            this.cardCvvDeclined = cardCvvDeclined;
        }
    }

    public static CardInfoDeclined getCardInfoDeclined() {
        return new CardInfoDeclined("5555 6666 7777 8888", "12", "23", "777");

    }

}



