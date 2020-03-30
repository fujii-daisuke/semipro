package red.semipro;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.File;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.stripe.model.Transfer;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountCreateParams.Company;
import com.stripe.param.AccountCreateParams.RequestedCapability;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.TransferCreateParams;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class StripeTest {

    /**
     * 顧客の作成
     *
     * @throws StripeException
     */
    @Test
    public void createCustomer() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Map<String, Object> params = new HashMap<>();
        params.put(
            "description",
            "Customer for d.ziifuu@gmail.com"
        );

        Customer customer = Customer.create(params);
        System.out.println(customer);
    }

    /**
     * 顧客のカード情報登録 sourceにはStrip.jsで作成したカード情報のトークンを設定
     *
     * @throws StripeException
     */
    @Test
    public void createCustomerCard() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Customer customer = Customer.retrieve("cus_GsmIMNtJEIByOf");
        CustomerUpdateParams params = CustomerUpdateParams.builder()
            .setSource("tok_1FviVSEQOFvWv0QlStjagAPx")
            .build();
        customer.update(params);
        System.out.println(customer);
    }

    @Test
    public void createAccountCompany() throws StripeException {
        RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
            .setApiKey("sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn")
            .build();

        Company company = Company.builder()
            .setTaxId("12345")
            .build();

        AccountCreateParams accountCreateParams = new AccountCreateParams.Builder()
            .setCountry("JP")
            .setType(AccountCreateParams.Type.CUSTOM)
            .setEmail("sukiyaki366@gmail.com")
            .setBusinessType("company")
            .setCompany(company)
//            .setTosAcceptance(tosAcceptance)
            .build();

        Account account = Account.create(accountCreateParams, requestOptions);
        System.out.println(account);
    }

    /**
     * 子アカウントの作成
     */
    @Test
    public void create() throws StripeException {
        RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
            .setApiKey("sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn")
            .build();

        // 利用規約同意
        AccountCreateParams.TosAcceptance tosAcceptance = AccountCreateParams.TosAcceptance
            .builder()
            .setDate((long) System.currentTimeMillis() / 1000L)
            .setIp("127.0.0.0")
            .build();

        AccountCreateParams.Individual.AddressKanji addressKanji = AccountCreateParams.Individual.AddressKanji
            .builder()
            .setCity("さいたま市")
            .setLine1("２１３２−７")
            .setPostalCode("3360911")
            .setState("埼玉県")
//            .setTown("緑区　三室")
            .build();

        AccountCreateParams.Individual.AddressKana addressKana = AccountCreateParams.Individual.AddressKana
            .builder()
            .setCity("サイタマシ")
//            .setLine1("２１３２−７")
            .setPostalCode("3310000")
            .setState("サイタマケン")
//            .setTown("ミドリク　ミムロ")
            .build();

        AccountCreateParams.Individual.Dob dob =
            AccountCreateParams.Individual.Dob.builder()
                .setYear(1980L)
                .setMonth(9L)
                .setDay(29L)
                .build();

        AccountCreateParams.Individual individual = AccountCreateParams.Individual.builder()
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

        AccountCreateParams accountCreateParams = new AccountCreateParams.Builder()
            .addAllRequestedCapability(List.of(RequestedCapability.CARD_PAYMENTS, RequestedCapability.TRANSFERS))
            .setCountry("JP")
            .setType(AccountCreateParams.Type.CUSTOM)
            .setEmail("sukiyaki366@gmail.com")
            .setBusinessType("individual")
            .setIndividual(individual)
            .setTosAcceptance(tosAcceptance)
            .build();

        Account account = Account.create(accountCreateParams, requestOptions);
        System.out.println(account);
    }

    /**
     * 子アカウント情報
     */
    @Test
    public void retrieveTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Account account = Account.retrieve("acct_1GBIATF21fTy4BVp");
        System.out.println(account);
    }

    @Test
    public void createTokenTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Map<String, Object> bankAccount = new HashMap<>();
        bankAccount.put("country", "JP");
        bankAccount.put("currency", "jpy");
        bankAccount.put("account_holder_name", "フジイ　ダイスケ");
        bankAccount.put("account_holder_type", "individual");
        bankAccount.put("routing_number", "0017258");
        bankAccount.put("account_number", "3921677");
        Map<String, Object> params = new HashMap<>();
        params.put("bank_account", bankAccount);

        Token token = Token.create(params);
        System.out.println(token);
    }

    /**
     * 子アカウントの本人確認書類の提出
     *
     * @see <a href="https://qiita.com/y_toku/items/7bfa42793801dfc5415d#custom-%E3%82%A2%E3%82%AB%E3%82%A6%E3%83%B3%E3%83%88%E3%82%92%E4%BD%9C%E6%88%90%E3%81%99%E3%82%8B">アカウントを作成する</a>
     */
    @Test
    public void identityVerificationTest() throws StripeException {
        //TODO pending
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";
        RequestOptions requestOptions = RequestOptions.builder()
            .setStripeAccount("acct_1FklGoCINNLsYkH7")
            .build();

        Map<String, Object> fileUploadParams = new HashMap<String, Object>();
        fileUploadParams.put("purpose", "identity_document");
        fileUploadParams
            .put("file", new java.io.File("/Users/fujiidaisuke/Pictures/sample/dog.jpg"));

        File.create(fileUploadParams, requestOptions);
    }

    /**
     * 子アカウントの銀行口座情報の登録
     *
     * @see <a href="https://stripe.com/docs/stripe-js/v2#collecting-bank-account-details">Collecting
     * bank account details</a>
     */
    @Test
    public void createBank() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        Account account = Account.retrieve("acct_1FklGoCINNLsYkH7");

        Map<String, Object> params = new HashMap<>();
        params.put("external_account", "btok_1FvvSlEQOFvWv0QlpmLlehHF");

        account.getExternalAccounts().create(params);
        System.out.println(account);
    }

    /**
     * Separate Charges and Transfers プラットフォームにチャージ
     *
     * @see <a href="https://qiita.com/y_toku/items/7bfa42793801dfc5415d#custom-%E3%82%A2%E3%82%AB%E3%82%A6%E3%83%B3%E3%83%88%E3%82%92%E4%BD%9C%E6%88%90%E3%81%99%E3%82%8B">Separate
     * Charges and Transfers: 支払いと送金を分ける</a>
     * @see <a href="https://stripe.com/docs/connect/testing#account-numbers">Testing Stripe
     * Connect</a>
     */
    @Test
    public void chargeTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        ChargeCreateParams chargeCreateParams = new ChargeCreateParams.Builder()
            .setAmount(10000l)
            .setCurrency("JPY")
            .setSource("card_1FviVSEQOFvWv0Qlzi8ZjaXV")
            .setTransferGroup("12345")
            .setCustomer("cus_GSWxccCcQQKyp5")
            .build();

        Charge charge = Charge.create(chargeCreateParams);
        System.out.println(charge);
    }

    /**
     * Separate Charges and Transfers 子アカウントへ送金
     */
    @Test
    public void transferTest() throws StripeException {
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";
        TransferCreateParams params = TransferCreateParams.builder()
            .setAmount(7000l)
            .setCurrency("jpy")
            .setDestination("acct_1FklGoCINNLsYkH7")
            .setTransferGroup("12345")
            .build();

        Transfer transfer = Transfer.create(params);
        System.out.println(transfer);
    }

    /**
     * プラットフォームから顧客へ返金
     */
    @Test
    public void refundTest() throws StripeException {
        // TODO　プラットフォームへの入金が未完の為、テスト未確認
        Stripe.apiKey = "sk_test_KoBmJmYjEn1OfoBb9b8H1HRB00DojSQMTn";

        RefundCreateParams params = RefundCreateParams.builder()
            .setCharge("ch_1Fvb9iEQOFvWv0QlL61b72dI")
            .build();
        Refund refund = Refund.create(params);
        System.out.println(refund);
    }

    public void payoutTest() {
        //TODO
    }
}
