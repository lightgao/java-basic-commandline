package PactJVMConsumerJunitExamples;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.DslPart;
import au.com.dius.pact.consumer.PactDslJsonBody;
import au.com.dius.pact.model.PactFragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



// --------------------------
// Run this test will generate "pact" file in directory "target/pacts/" with filename "ConsumerName-ProviderName.json"
// content of function createFragment() will determined the request & response content in this pact file.
// function runTest() will trigger the request if test is ran
// --------------------------

public class ExampleJavaConsumerPactTest extends ConsumerPactTest {

    @Override
    protected PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("testreqheader", "testreqheadervalue");

        DslPart body = new PactDslJsonBody()
                .object("data")
                    .stringType("name")
                    .booleanType("happy")
                    .hexValue("hexCode")
                    .id()
                    .ipAddress("localAddress")
                    .numberValue("age", 100)
                    .timestamp()

                    .object("obj1")
                        .stringType("obj1-string-field", "111")
                        .booleanType("obj1-boolean-field", true)

                        .minArrayLike("users", 0)
                            .id()
                            .stringType("name")
                            .object("obj3")
                                .stringType("obj3-string-field", "333")
                                .booleanType("obj3-boolean-field", true)
                                .object("obj4")
                                    .stringType("obj4-string-field", "444")
                                    .booleanType("obj4-boolean-field", false)
                                .closeObject()
                            .closeObject()
                            .closeObject()
                        .closeArray()

                        .object("obj2")
                            .stringType("obj2-string-field", "222")
                            .booleanType("obj2-boolean-field", false)
                        .closeObject()

                    .closeObject()

                .closeObject();

        return builder
                .given("test state") // NOTE: Using provider states are optional, you can leave it out
                    .uponReceiving("a request for something")
                .path("/")
                .method("GET")
                .headers(headers)
//                    .body("{\"test\":true}")
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body(body).toFragment();
    }


    @Override
    protected String providerName() {
        return "ProviderName";
    }

    @Override
    protected String consumerName() {
        return "ConsumerName";
    }

    @Override
    protected void runTest(String url) throws IOException {
        try {
            URL u = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


//        assertEquals(new ProviderClient(url).getSomething(), "{\"responsetest\":true}");
    }
}
