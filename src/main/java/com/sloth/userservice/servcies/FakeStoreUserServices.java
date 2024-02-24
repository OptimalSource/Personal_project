package com.sloth.userservice.servcies;


import com.sloth.userservice.DTOs.RequestDTO;
import com.sloth.userservice.DTOs.ResponseDTO;
import com.sloth.userservice.model.Address;
import com.sloth.userservice.model.GeoLocation;
import com.sloth.userservice.model.Name;
import com.sloth.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FakeStoreUserServices implements IUserServices{

    RestTemplate restTemplate;
    @Autowired
    public FakeStoreUserServices(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }
    //we can wrap inside try catch block for HTTPExceptions
    @Override
    public Optional<List<User>> getAllUser() {
        ResponseDTO[] responseDTOS=restTemplate.getForObject(
                "https://fakestoreapi.com/users",
                ResponseDTO[].class
        );
        if(responseDTOS != null){
            List<User> users=new ArrayList<>();

            for(ResponseDTO responseDTO: responseDTOS){
                users.add(getUserFromResponseAPI(responseDTO));
            }
            return Optional.of(users);
        }
        return Optional.empty();
    }

    private User getUserFromResponseAPI(ResponseDTO responseDTO) {
        User user = new User();
        user.setId(responseDTO.getId());
        user.setEmail(responseDTO.getEmail());
        user.setUsername(responseDTO.getUsername());
        user.setPassword(responseDTO.getPassword());
        user.setPhone(responseDTO.getPhone());
        user.setName(convertNametoObject(responseDTO.getName()));
        user.setAddress(convertAddresstoObject(responseDTO.getAddress()));
        return user;
    }

    private Name convertNametoObject(Name response) {
        Name name=new Name();
        name.setFirstname(response.getFirstname());
        name.setLastname(response.getLastname());
        return name;
    }
    private Address convertAddresstoObject(Address response){
        Address address=new Address();
        address.setCity(response.getCity());
        address.setNumber(response.getNumber());
        address.setZipcode(response.getZipcode());
        address.setStreet(response.getStreet());
        address.setGeolocation(convertGeoLocationtoObject(response.getGeolocation()));
        return address;
    }

    private GeoLocation convertGeoLocationtoObject(GeoLocation response) {
        if(response !=null){
            GeoLocation geoLocation=new GeoLocation();
            geoLocation.setLat(response.getLat());
            geoLocation.setLng(response.getLng());
            return geoLocation;
        }
        return response;
    }

    @Override
    public Optional<User> getSingleUser(Long Id) {
        try {
            ResponseDTO responseDTO = restTemplate.getForObject(
                    "https://fakestoreapi.com/users/" + Id,
                    ResponseDTO.class
            );
            if(responseDTO !=null){
                return Optional.of(getUserFromResponseAPI(responseDTO));
            }
        }catch (HttpClientErrorException e){
            e.printStackTrace(); //basic handling, can improve handling by checking status code e.getStatusCode()
        }
        return Optional.empty();
    }

    @Override
    public User addNewUser(RequestDTO requestDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestDTO> httpEntity=new HttpEntity<>(requestDTO, httpHeaders);

        try{
            ResponseDTO responseDTO=restTemplate.postForObject(
                    "https://fakestoreapi.com/users",
                    httpEntity,
                    ResponseDTO.class
            );
            return getUserFromResponseAPI(responseDTO);
        }catch(HttpClientErrorException e){
            System.out.println(e.getStatusCode() +"\n" + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteUser(Long Id) {
        try{
            restTemplate.delete(
                    "https://fakestoreapi.com/users/{id}", Id
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateUser(Long id, RequestDTO requestDTO) {
        Optional<User> findUser=getSingleUser(id);
        if(findUser.isPresent()){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity httpEntity = new HttpEntity<>(requestDTO, httpHeaders);

//            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//            requestFactory.setConnectTimeout(300);
//            restTemplate.setRequestFactory(requestFactory);

            ResponseEntity<ResponseDTO> responseDTO= restTemplate.exchange(
                    "https://fakestoreapi.com/users/"+id,
                    HttpMethod.PUT,
                    httpEntity,
                    ResponseDTO.class
            );
            return getUserFromResponseAPI(responseDTO.getBody());
        }
        return null;
    }
}
