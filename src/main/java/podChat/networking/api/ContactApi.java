package podChat.networking.api;


import podChat.mainmodel.SearchContactVO;
import podChat.mainmodel.UpdateContact;
import podChat.model.ContactRemove;
import podChat.model.Contacts;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ContactApi {

    @POST("nzh/addContacts")
    @FormUrlEncoded
    Call<Contacts> addContact(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("firstName") String firstName
            , @Field("lastName") String lastName
            , @Field("email") String email
            , @Field("username") String username
            , @Field("uniqueId") String uniqueId
            , @Field("cellphoneNumber") String cellphoneNumber
    );

    /* addContact Without type code */

    @POST("nzh/addContacts")
    @FormUrlEncoded
    Call<Contacts> addContact(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("firstName") String firstName
            , @Field("lastName") String lastName
            , @Field("email") String email
            , @Field("uniqueId") String uniqueId
            , @Field("cellphoneNumber") String cellphoneNumber
            , @Field("username") String username
            , @Field("typeCode") String typeCode
            , @Field("ownerId") long ownerId
    );

    /* addContacts With type code*/
    @POST("nzh/addContacts")
    @FormUrlEncoded
    Call<Response<Contacts>> addContacts(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("firstName") ArrayList<String> firstName
            , @Field("lastName") ArrayList<String> lastName
            , @Field("email") ArrayList<String> email
            , @Field("uniqueId") ArrayList<String> uniqueId
            , @Field("cellphoneNumber") ArrayList<String> cellphoneNumber
            , @Field("typeCode") ArrayList<String> typeCode
    );

    /* addContacts Without type code*/
    @POST("nzh/addContacts")
    @FormUrlEncoded
    Call<Response<Contacts>> addContacts(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("firstName") ArrayList<String> firstName
            , @Field("lastName") ArrayList<String> lastName
            , @Field("email") ArrayList<String> email
            , @Field("uniqueId") ArrayList<String> uniqueId
            , @Field("cellphoneNumber") ArrayList<String> cellphoneNumber
    );


    @POST("nzh/removeContacts")
    @FormUrlEncoded
    Call<ContactRemove> removeContact(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("id") long userId);


    @POST("nzh/removeContacts")
    @FormUrlEncoded
    Call<ContactRemove> removeContact(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("id") long userId
            , @Field("typeCode") String typeCode
    );

    /* Update contact without type code*/
    @POST("nzh/updateContacts")
    @FormUrlEncoded
    Call<UpdateContact> updateContact(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("id") long id
            , @Field("firstName") String firstName
            , @Field("lastName") String lastName
            , @Field("email") String email
            , @Field("uniqueId") String uniqueId
            , @Field("cellphoneNumber") String cellphoneNumber);


    /* Update contact with type code*/
    @POST("nzh/updateContacts")
    @FormUrlEncoded
    Call<UpdateContact> updateContact(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Field("id") long id
            , @Field("firstName") String firstName
            , @Field("lastName") String lastName
            , @Field("email") String email
            , @Field("uniqueId") String uniqueId
            , @Field("cellphoneNumber") String cellphoneNumber
            , @Field("typeCode") String typeCode
            , @Field("ownerId") long ownerId);

    @GET("nzh/listContacts")
    Call<SearchContactVO> searchContacts(@Header("_token_") String token
            , @Header("_token_issuer_") int tokenIssuer
            , @Query("id") String id
            , @Query("firstName") String firstName
            , @Query("lastName") String lastName
            , @Query("email") String email
            , @Query("offset") String offset
            , @Query("size") String size
            , @Query("typeCode") String typeCode
            , @Query("q") String query
            , @Query("cellphoneNumber") String cellphoneNumber
            , @Query("ownerId") long ownerId);
}
