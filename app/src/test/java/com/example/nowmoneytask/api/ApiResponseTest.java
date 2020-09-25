package com.example.nowmoneytask.api;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
@RunWith(JUnit4.class)
public class ApiResponseTest {

    @Test
    public void exception() {
        Exception exception = new Exception("error");
        ApiResponse<String> apiResponse = new ApiResponse<>(exception);
        assertThat(apiResponse.links, notNullValue());
        assertThat(apiResponse.body, nullValue());
        assertThat(apiResponse.code, is(500));
        assertThat(apiResponse.errorMessage, is("error"));
    }

    @Test
    public void success() {
        ApiResponse<String> apiResponse = new ApiResponse<>(Response.success("ahmed"));
        assertThat(apiResponse.errorMessage, nullValue());
        assertThat(apiResponse.code, is(200));
        assertThat(apiResponse.body, is("ahmed"));
        assertThat(apiResponse.getNextPage(), is(nullValue()));
    }

    @Test
    public void error() {
        ApiResponse<String> response = new ApiResponse<String>(Response.error(400,
                ResponseBody.create(MediaType.parse("application/txt"), "error")));
        assertThat(response.code, is(400));
        assertThat(response.errorMessage, is("error"));
    }



}