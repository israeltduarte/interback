package br.com.iser.interback.mock;

import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.dto.CustomerRequestDTO;
import br.com.iser.interback.dto.SingleDigitDTO;
import br.com.iser.interback.dto.SingleDigitRequestDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.entity.Key;
import br.com.iser.interback.entity.SingleDigit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockGenerator {

  public static final String NAME_GABRIELLE = "Gabrielle Almeida";
  public static final String EMAIL_GABRIELLE = "gabrielle.almeida@gmail.com";
  public static final String NAME_LILIAN = "Lilian Terra";
  public static final String EMAIL_LILIAN = "lilian.terra@gmail.com";

  public static Customer getCustomerWithSingleDigit() {

    return Customer.builder().id(1L).name(NAME_LILIAN).key(getInactiveKey()).email(EMAIL_LILIAN).singleDigits(
        Collections.singletonList(getSingleDigit())).build();
  }

  public static Customer getCustomerWithoutSingleDigit() {

    return Customer.builder().id(2L).name(NAME_GABRIELLE).email(EMAIL_GABRIELLE).singleDigits(new ArrayList<>()).build();
  }

  public static CustomerDTO getCustomerDTOWithSingleDigit() {

    return CustomerDTO.builder().name(NAME_LILIAN).email(EMAIL_LILIAN).singleDigits(Collections.singletonList(getSingleDigit()))
        .build();
  }

  public static SingleDigit getSingleDigit() {

    LocalDateTime time = LocalDateTime.of(2021, 12, 20, 15, 0);

    return SingleDigit.builder().id(1L).number("12").multiplier(2).concatenated("1212").result(6).createdAt(time).build();
  }

  public static SingleDigitDTO getSingleDigitDTO() {

    LocalDateTime time = LocalDateTime.of(2021, 12, 20, 15, 0);

    return SingleDigitDTO.builder().number("12").multiplier(2).concatenated("1212").result(6).createdAt(time).build();
  }

  public static List<Customer> getListCustomerWithSingleDigit() {

    return Arrays.asList(getCustomerWithSingleDigit(), getCustomerWithoutSingleDigit());
  }

  public static List<Customer> getListCustomerWithoutSingleDigit() {

    return Arrays.asList(getCustomerWithoutSingleDigit(), getCustomerWithoutSingleDigit());
  }

  public static List<SingleDigitDTO> getListSingleDigitsDTO() {

    return Arrays.asList(getSingleDigitDTO(), getSingleDigitDTO());
  }

  public static List<SingleDigit> getListSingleDigits() {

    return Collections.singletonList(getSingleDigit());
  }

  public static CustomerRequestDTO getCustomerRequestDTO() {

    return CustomerRequestDTO.builder().name(NAME_LILIAN).email(EMAIL_LILIAN).build();
  }

  public static SingleDigitRequestDTO getSingleDigitRequestDTO() {

    return SingleDigitRequestDTO.builder().customerId(1L).multiplier(2).number("12").build();
  }

  public static Customer getEncryptedCustomer() {

    return Customer.builder()
        .id(1L)
        .name(
            "XtITjnfx6dbvaUlyFsmFMSo1rfliVfsWdDEEtlHLHv32pQccUG3+U9IdQPsbiEX1zGwwarJVELnlB+mTVZGISrwTBvUzlaleR5zC1tUqr3VTCpB8xNCCJdmnnpz6NwGn+auJfYtq3LUpjjjgF8hRk79gV+O5uqv50We2LuiH+GJRtDspd2uWJBAvK5GXBWAVjsSALiYr6rbC9Wj40NQJTecG/BZmLSEj9UwJd8sBx8euQuiAw+U2J/Biix0nnD3F3z1ceIsDWOxvXae/Wh8qNAMF5EkHtxsNJTFneKKVlBmWpLRAVHKZjFXdBk9e+b9IVlu6to08RPV5M9VMLi/dBA==")
        .email(
            "LW9cbbxGvzsNvKu+44qWm2oDXc7zA2lFy3YHE6cMVcsmSToaQVr+uDJJ71aBvPOZTk/oGpUcDGGBQRWiyLg4NI3R5K2kwGWGqUeTHsA8mZiQk4mYy8H8iuQlpVM/1vkkT4+cVaK94t5RQU4YpF34aWfmMIKK6YkCs76vMSqMHm1pyFLtzaTy0XOmk67hPk1lPvyl65WgTAw6ZnPBRlbJVuv7rMyvSqg5Gfx1iSEL7/d1OoFSYpohRTXEDd0O8ak4iAD6DEEp6ZXq+F4zrwnFg+NLiDc3Eni3pAaHpCpqh4tyAEIpnI+rcznwQTSoq56cSPFZoIQVAFDddb1/J64b8Q==")
        .key(getKey())
        .singleDigits(new ArrayList<>())
        .build();
  }

  public static CustomerDTO getEncryptedCustomerDTO() {

    return CustomerDTO.builder()
        .name(
            "XtITjnfx6dbvaUlyFsmFMSo1rfliVfsWdDEEtlHLHv32pQccUG3+U9IdQPsbiEX1zGwwarJVELnlB+mTVZGISrwTBvUzlaleR5zC1tUqr3VTCpB8xNCCJdmnnpz6NwGn+auJfYtq3LUpjjjgF8hRk79gV+O5uqv50We2LuiH+GJRtDspd2uWJBAvK5GXBWAVjsSALiYr6rbC9Wj40NQJTecG/BZmLSEj9UwJd8sBx8euQuiAw+U2J/Biix0nnD3F3z1ceIsDWOxvXae/Wh8qNAMF5EkHtxsNJTFneKKVlBmWpLRAVHKZjFXdBk9e+b9IVlu6to08RPV5M9VMLi/dBA==")
        .email(
            "LW9cbbxGvzsNvKu+44qWm2oDXc7zA2lFy3YHE6cMVcsmSToaQVr+uDJJ71aBvPOZTk/oGpUcDGGBQRWiyLg4NI3R5K2kwGWGqUeTHsA8mZiQk4mYy8H8iuQlpVM/1vkkT4+cVaK94t5RQU4YpF34aWfmMIKK6YkCs76vMSqMHm1pyFLtzaTy0XOmk67hPk1lPvyl65WgTAw6ZnPBRlbJVuv7rMyvSqg5Gfx1iSEL7/d1OoFSYpohRTXEDd0O8ak4iAD6DEEp6ZXq+F4zrwnFg+NLiDc3Eni3pAaHpCpqh4tyAEIpnI+rcznwQTSoq56cSPFZoIQVAFDddb1/J64b8Q==")
        .publicKey(
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgY0wJnEXW4T32vjoPNL5a1etYVRcEsPMsRD6ZAv9mjHokuYtTk1w0KZGylduria3dNP7Qhgmo6K8ZwUwqxWKBtCuA5qNh+j74U0+/9o8+mr0Gxb6uQN6uWGHTw13OgR3PlOL/rURsP9h+nE/MD/k0aadph+wiUFTZCeichkT5dq6ZDnYEND0gbws4B+plNIr1x72JHBMD28k1dAjMDzZYB64IIKuqUCIfxpQnJfNn4D9JitrHbKWnjZ+epbFttillBCb4TOqerzZ+I3kzOte6sW0L3+a99NuWKn9qrgs9xJXIDxeYpw7XU+ROWH8LEZUPzcwMFs8tHYvi3kDDWXs1QIDAQAB")
        .singleDigits(new ArrayList<>())
        .build();
  }

  private static Key getKey() {

    return Key.builder()
        .id(1L)
        .isActive(Boolean.TRUE)
        .publicKey(
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlhZbuDg+MlBhel78VComCgjRQWg6/Is/rmS7DDr1KcaN5ompVB3lfhrphfKlQcISYFm2YDmEs0lZJdZ8jOJRAlQijCs0z23BYgYoK9YRdY/2Mhz7YvYzZG/4LLQ3wnhEbX3amhcOZze+Gavm7ktkP3AucOlYOzh2Qqi45ZH5V2HpHQHt5NnvpFd1DpxjlXwaL5Wd5EorcPjQBPOX2paP0b0DUYwFP8V31In6DY8vMPpUR0dJ/IR179OdWx1WDTGesHoXqbopDXE507CeweyBp269dWHIc7KQYrFjUuyNJ6TWBlHrCe/3NUar6gTvHPHw1kTROi0AXt7ykx9a9KBRHQIDAQAB")
        .privateKey(
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWFlu4OD4yUGF6XvxUKiYKCNFBaDr8iz+uZLsMOvUpxo3mialUHeV+GumF8qVBwhJgWbZgOYSzSVkl1nyM4lECVCKMKzTPbcFiBigr1hF1j/YyHPti9jNkb/gstDfCeERtfdqaFw5nN74Zq+buS2Q/cC5w6Vg7OHZCqLjlkflXYekdAe3k2e+kV3UOnGOVfBovlZ3kSitw+NAE85falo/RvQNRjAU/xXfUifoNjy8w+lRHR0n8hHXv051bHVYNMZ6wehepuikNcTnTsJ7B7IGnbr11YchzspBisWNS7I0npNYGUesJ7/c1RqvqBO8c8fDWRNE6LQBe3vKTH1r0oFEdAgMBAAECggEAVLtkppkiC/ezQIm1fLKa7RlTQdDdVEws9ICr1NjIJgCRhfB75yBFuq7o3ZVCVOCCzWB4hNSUJT+ok6MGh+cRAvaK1oVh+CUngvak4TKmgq92t44DIjiKhQ5tlpdbz29kekgODOYtXHps2UNiSW/ItcPYhChIho52vWVRlVGW6gxgXPjGJVsFn3oO8XpudZCaiVUKSn7oTnTcOZ/f4Tbr3ZY/snKwBOLzyM3cFojx5bcathLMmeqipD4840l+7pknqrC16fMPKjmzNhDhGZmLRjArtVhXVZmo6FGBm7oowpRhmf5ulapTOZ8H5v2GfrzDfFgM6ImuIuKCLYlc3NRphQKBgQDGw7tVuLHNNM4MRT5eqZ1K4+zzN7PhHNkbIzdF9H6hoOUBDFP86K4cSWQ9FyZLbAdbxFUYWCv58eJgO4deaeQA8pBAKqAlaEKNE2NLO1/Rxml23MO5k4Tywk34WtCrjN7iKklTTM32nRM77HKohcg3xTNwvFFXB0Sep7N7l7OmRwKBgQDBTk0Vb2GznaehCsjptWONVg3lVKTUKV6ae2a/csm3X8Z5UNHOdTlhkJolWvN2bBD2TZbdLO1aGl+wAvHp7/rpB5sx7nqI9CTjX0nkq9iTn4rChxH54i9M7BZhizJmS0kcIg3/3R7XTwB+0WkjZ66qQlB6OD9uKoU5XuXDfdKrewKBgGTxVHtlUA76KUZ8B//FPhehh13BxT2nC+9HRDPnxkPJVWPh4emINZwx/ZE054LYOyh0ng6+AJ9wI1piAQ6G0n9UsMOVoj09JMdGuF+lqLT9geQWm+PsMlGjMjvVyzHEWhEcDp8aW2NKRFFjdJt3TcZdBRHU4a5EB6pXWsSCt37TAoGBAJAQFO6mpb/A/Q7OBNH4Nws8WPrSzEy50CF9WiPQHnklTbtX+iTNxEfsBpVUZSGahEGrrYWEWBfQlAOPDk90ZCCLZKQfwP6URXGWsHFViQDr4/P5yfHbGblQMZXcsWD/NqtazegkGToJTKs20g5q1QdaiLOte8hUtBj/mmjJOtpFAoGBAICP5IFrIbAUOegbqvbqCag3SWO0ZydrOiyXAao5fDNfwy+wDGep6NYLcEeCqWQVFWUTHF0UVQsRg9oRlCtNBzqc2JAiri2dSF6OId8MaMSBv9WctCqvsJ+uYujXaOuqejNF+ZC7ah/f+mOFYvBSDZ9E/sgcakm2nGDzXTVHBjEA")
        .build();
  }

  public static Key getInactiveKey() {

    return Key.builder()
        .isActive(Boolean.FALSE)
        .publicKey(
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlhZbuDg+MlBhel78VComCgjRQWg6/Is/rmS7DDr1KcaN5ompVB3lfhrphfKlQcISYFm2YDmEs0lZJdZ8jOJRAlQijCs0z23BYgYoK9YRdY/2Mhz7YvYzZG/4LLQ3wnhEbX3amhcOZze+Gavm7ktkP3AucOlYOzh2Qqi45ZH5V2HpHQHt5NnvpFd1DpxjlXwaL5Wd5EorcPjQBPOX2paP0b0DUYwFP8V31In6DY8vMPpUR0dJ/IR179OdWx1WDTGesHoXqbopDXE507CeweyBp269dWHIc7KQYrFjUuyNJ6TWBlHrCe/3NUar6gTvHPHw1kTROi0AXt7ykx9a9KBRHQIDAQAB")
        .privateKey(
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWFlu4OD4yUGF6XvxUKiYKCNFBaDr8iz+uZLsMOvUpxo3mialUHeV+GumF8qVBwhJgWbZgOYSzSVkl1nyM4lECVCKMKzTPbcFiBigr1hF1j/YyHPti9jNkb/gstDfCeERtfdqaFw5nN74Zq+buS2Q/cC5w6Vg7OHZCqLjlkflXYekdAe3k2e+kV3UOnGOVfBovlZ3kSitw+NAE85falo/RvQNRjAU/xXfUifoNjy8w+lRHR0n8hHXv051bHVYNMZ6wehepuikNcTnTsJ7B7IGnbr11YchzspBisWNS7I0npNYGUesJ7/c1RqvqBO8c8fDWRNE6LQBe3vKTH1r0oFEdAgMBAAECggEAVLtkppkiC/ezQIm1fLKa7RlTQdDdVEws9ICr1NjIJgCRhfB75yBFuq7o3ZVCVOCCzWB4hNSUJT+ok6MGh+cRAvaK1oVh+CUngvak4TKmgq92t44DIjiKhQ5tlpdbz29kekgODOYtXHps2UNiSW/ItcPYhChIho52vWVRlVGW6gxgXPjGJVsFn3oO8XpudZCaiVUKSn7oTnTcOZ/f4Tbr3ZY/snKwBOLzyM3cFojx5bcathLMmeqipD4840l+7pknqrC16fMPKjmzNhDhGZmLRjArtVhXVZmo6FGBm7oowpRhmf5ulapTOZ8H5v2GfrzDfFgM6ImuIuKCLYlc3NRphQKBgQDGw7tVuLHNNM4MRT5eqZ1K4+zzN7PhHNkbIzdF9H6hoOUBDFP86K4cSWQ9FyZLbAdbxFUYWCv58eJgO4deaeQA8pBAKqAlaEKNE2NLO1/Rxml23MO5k4Tywk34WtCrjN7iKklTTM32nRM77HKohcg3xTNwvFFXB0Sep7N7l7OmRwKBgQDBTk0Vb2GznaehCsjptWONVg3lVKTUKV6ae2a/csm3X8Z5UNHOdTlhkJolWvN2bBD2TZbdLO1aGl+wAvHp7/rpB5sx7nqI9CTjX0nkq9iTn4rChxH54i9M7BZhizJmS0kcIg3/3R7XTwB+0WkjZ66qQlB6OD9uKoU5XuXDfdKrewKBgGTxVHtlUA76KUZ8B//FPhehh13BxT2nC+9HRDPnxkPJVWPh4emINZwx/ZE054LYOyh0ng6+AJ9wI1piAQ6G0n9UsMOVoj09JMdGuF+lqLT9geQWm+PsMlGjMjvVyzHEWhEcDp8aW2NKRFFjdJt3TcZdBRHU4a5EB6pXWsSCt37TAoGBAJAQFO6mpb/A/Q7OBNH4Nws8WPrSzEy50CF9WiPQHnklTbtX+iTNxEfsBpVUZSGahEGrrYWEWBfQlAOPDk90ZCCLZKQfwP6URXGWsHFViQDr4/P5yfHbGblQMZXcsWD/NqtazegkGToJTKs20g5q1QdaiLOte8hUtBj/mmjJOtpFAoGBAICP5IFrIbAUOegbqvbqCag3SWO0ZydrOiyXAao5fDNfwy+wDGep6NYLcEeCqWQVFWUTHF0UVQsRg9oRlCtNBzqc2JAiri2dSF6OId8MaMSBv9WctCqvsJ+uYujXaOuqejNF+ZC7ah/f+mOFYvBSDZ9E/sgcakm2nGDzXTVHBjEA")
        .build();
  }

  public static String getPublicKey() {

    return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlhZbuDg+MlBhel78VComCgjRQWg6/Is/rmS7DDr1KcaN5ompVB3lfhrphfKlQcISYFm2YDmEs0lZJdZ8jOJRAlQijCs0z23BYgYoK9YRdY/2Mhz7YvYzZG/4LLQ3wnhEbX3amhcOZze+Gavm7ktkP3AucOlYOzh2Qqi45ZH5V2HpHQHt5NnvpFd1DpxjlXwaL5Wd5EorcPjQBPOX2paP0b0DUYwFP8V31In6DY8vMPpUR0dJ/IR179OdWx1WDTGesHoXqbopDXE507CeweyBp269dWHIc7KQYrFjUuyNJ6TWBlHrCe/3NUar6gTvHPHw1kTROi0AXt7ykx9a9KBRHQIDAQAB";
  }
}