package red.semipro;

import java.util.HashMap;
import java.util.Map;

import com.stripe.net.RequestOptions;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountUpdateParams;
import com.stripe.param.ChargeCreateParams;
import org.junit.Test;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.model.Transfer;

public class StripeTest {

    /**
     * コネクトアカウントの作成
     */
    @Test
    public void create() throws StripeException {
        RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
                .setApiKey("sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn")
                .build();

        AccountCreateParams accountCreateParams = new AccountCreateParams.Builder()
                                                        .setCountry("JP")
                                                        .setType(AccountCreateParams.Type.CUSTOM)
                                                        .setEmail("sukiyaki366@gmail.com")
                                                        .build();

        Account account = Account.create(accountCreateParams, requestOptions);
        System.out.println(account);
    }

    /**
     * 利用規約への同意
     */
    @Test
    public void tosAcceptance() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Account account = Account.retrieve("acct_1FghDYCY7ikIEVsM");

        AccountUpdateParams.TosAcceptance tosAcceptance = new AccountUpdateParams.TosAcceptance.Builder()
                .setDate((long) System.currentTimeMillis() / 1000L)
                .setIp("127.0.0.0")
                .build();

        AccountUpdateParams accountUpdateParams = new AccountUpdateParams.Builder()
                .setTosAcceptance(tosAcceptance)
                .build();

        account.update(accountUpdateParams);
        System.out.println(account);
    }

    /**
     * アカウント情報の取得
     */
    @Test
    public void retrieve() throws StripeException {
        RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
                .setApiKey("sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn")
                .setStripeAccount("acct_1FghDYCY7ikIEVsM")
                .build();

        Account account = Account.retrieve(requestOptions);
        System.out.println(account);
    }

    @Test
    public void accountUpdate() throws  StripeException {
        RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
                .setApiKey("sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn")
                .setStripeAccount("acct_1FghDYCY7ikIEVsM")
                .build();

        Account account = Account.retrieve(requestOptions);

        // 利用規約同意
        AccountUpdateParams.TosAcceptance tosAcceptance = AccountUpdateParams.TosAcceptance.builder()
                .setDate((long) System.currentTimeMillis() / 1000L)
                .setIp("127.0.0.0")
                .build();

        AccountUpdateParams.Individual.AddressKana addressKana = AccountUpdateParams.Individual.AddressKana.builder()
                .setCity("サイタマシ")
                .setLine1("２１３２−７")
                .setPostalCode("3360911")
                .setState("サイタマケン")
                .setTown("ミドリク　ミムロ")
                .build();

        AccountUpdateParams.Individual.AddressKanji addressKanji = AccountUpdateParams.Individual.AddressKanji.builder()
                .setCity("さいたま市")
                .setLine1("２１３２−７")
                .setPostalCode("3360911")
                .setState("埼玉県")
                .setTown("緑区　三室")
                .build();

        AccountUpdateParams.Individual.Dob dob =
                AccountUpdateParams.Individual.Dob.builder()
                        .setYear(1980L)
                        .setMonth(9L)
                        .setDay(29L)
                        .build();

        AccountUpdateParams.Individual individual = AccountUpdateParams.Individual.builder()
                .setFirstName("大介")
                .setLastName("藤井")
                .setFirstNameKanji("大介")
                .setLastNameKanji("藤井")
                .setFirstNameKana("だいすけ")
                .setLastNameKana("ふじい")
                .setGender("male")
                .setDob(dob)
                .setAddressKana(addressKana)
                .setAddressKanji(addressKanji)
                .setPhone("+819011112222")
                .build();

        AccountUpdateParams accountUpdateParams = new AccountUpdateParams.Builder()
                .setTosAcceptance(tosAcceptance)
                .setBusinessType("individual")
                .setIndividual(individual)
                .build();

        account.update(accountUpdateParams, requestOptions);
        System.out.println(account);
    }

    /**
     * プラットフォームからコネクトアカウントにチャージ
     */
    @Test
    public void charge() throws StripeException {
        RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
                .setApiKey("sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn")
                .build();

        Account account = Account.retrieve(requestOptions);
        ChargeCreateParams.TransferData transferData = new ChargeCreateParams.TransferData.Builder().setDestination("acct_1FGp0xKZb7ogTiAO").build();

        ChargeCreateParams chargeCreateParams = new ChargeCreateParams.Builder()
                .setAmount(1000l)
                .setCurrency("JPY")
                .setSource("tok_visa")
                .setTransferData(transferData)
                .setApplicationFee(200l)
                .build();

        Charge charge = Charge.create(chargeCreateParams, requestOptions);
        System.out.println(charge);
    }


    /**
     * カスタムアカウント作成
     */
    @Test
    public void createCustomAccountTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("country", "JP");
        params.put("type", "custom");
        params.put("email", "sukiyaki366@gmail.com");
        Account acct = Account.create(params);
        System.out.println(acct);
    }
    
    /**
     * カスタムアカウント更新
     */
    @Test
    public void updateCustomAccountTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Account acct = Account.retrieve("acct_1Et9QdLauNMFrXUW");
        
        Map<String, Object> individual = new HashMap<String, Object>();
        
        individual.put("last_name", "藤井");
        individual.put("first_name", "大介");
        individual.put("last_name_kanji", "藤井");
        individual.put("first_name_kanji", "大介");
        individual.put("last_name_kana", "ふじい");
        individual.put("first_name_kana", "だいすけ");
        individual.put("gender", "male");
        individual.put("phone", "+819058046429");
        individual.put("email", "d.ziifuu@gmail.com");
        
        Map<String, Object> addressKanji = new HashMap<String, Object>();
        addressKanji.put("postal_code", "3360911");
        addressKanji.put("state", "埼玉県");
        addressKanji.put("city", "さいたま市");
        addressKanji.put("town", "緑区三室");
        addressKanji.put("line1", "２１３２−７");
        individual.put("address_kanji", addressKanji);
        
        Map<String, Object> addressKana = new HashMap<String, Object>();
        addressKana.put("postal_code", "3360911");
        addressKana.put("state", "さいたまけん");
        addressKana.put("city", "さいたまし");
        addressKana.put("town", "みどりくみむろ");
        addressKana.put("line1", "２１３２ー７");
        individual.put("address_kana", addressKana);
        
        Map<String, Object> dob = new HashMap<String, Object>();
        dob.put("day", "29");
        dob.put("month", "09");
        dob.put("year", "1980");
        individual.put("dob", dob);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("business_type", "individual");
        params.put("individual", individual);
        
        Map<String, Object> externalAccount = new HashMap<String, Object>();
        externalAccount.put("object", "bank_account");
        externalAccount.put("country", "jp");
        externalAccount.put("currency", "jpy");
        externalAccount.put("account_number", "00012345");
        externalAccount.put("routing_number", "1100000");
        externalAccount.put("account_holder_name", "トクテスト(カ");
        params.put("external_account", externalAccount);
        
        acct = acct.update(params);
        System.out.println(acct);
     }
    
    /**
     * 利用規約への同意
     */
    @Test
    public void agreementAcceptanceTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Account acct = Account.retrieve("acct_1Et9QdLauNMFrXUW");

        Map<String, Object> tosAcceptanceParams = new HashMap<String, Object>();
        tosAcceptanceParams.put("date", (long) System.currentTimeMillis() / 1000L);
        tosAcceptanceParams.put("ip", "127.0.0.0"); // Assumes you're not using a proxy

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tos_acceptance", tosAcceptanceParams);

        acct = acct.update(params);
        System.out.println(acct);
    }
    
    /**
     * アカウントへ支払い
     */
    @Test
    public void destinationChargeTest() throws StripeException {
        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", 1000);
        params.put("currency", "jpy");
        params.put("source", "tok_visa");
        
        Map<String, Object> transferDataParams = new HashMap<String, Object>();
        transferDataParams.put("destination", "acct_1Et9QdLauNMFrXUW");
        
        params.put("transfer_data", transferDataParams);
        Charge charge = Charge.create(params);
        System.out.println(charge);
   }
    
    /**
     * 返金
     */
    @Test
    public void refundTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Map<String, Object> refundParams = new HashMap<String, Object>();
        refundParams.put("charge", "ch_1EtNuTEQOFvWv0QliMTaDHFh");
        refundParams.put("reverse_transfer", true);
        
        Refund refund = Refund.create(refundParams);
        
        System.out.println(refund);
   }

    /**
     * 子アカウントからプラットフォームへ送金を戻す
     */
    @Test
    public void reverseTransferTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Transfer transfer = Transfer.retrieve("tr_1EtNfxEQOFvWv0Qll5LrH3yn");
        
        System.out.println(transfer);
   }
}
