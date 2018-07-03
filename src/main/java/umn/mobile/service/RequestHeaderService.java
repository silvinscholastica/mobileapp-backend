package umn.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umn.mobile.model.RequestDetail;
import umn.mobile.model.RequestHeader;
import umn.mobile.repository.RequestDetailRepo;
import umn.mobile.repository.RequestHeaderRepo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class RequestHeaderService {
    @Autowired private RequestHeaderRepo requestHeaderRepo;
    @Autowired private RequestDetailRepo requestDetailRepo;

    public RequestHeaderService(){}

    public String createDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().format(formatter);
    }

    public Integer getCounter(){
        Integer i = requestHeaderRepo.getCounter();
        if(i==null){
            i=1;
        }
        else{
            i++;
        }

        return i;
    }

    public String createNomorDokumen() {
        return String.format(
                "PR/%s/%04d",
                String.format("%02d%02d",
                        LocalDate.now().getMonthValue(),
                        LocalDate.now().getYear()%100),
                getCounter()
        );
    }

    public String saveRequestHeader(RequestHeader requestHeader, List<RequestDetail> listOfRequestDetail){
        try {
            RequestHeader _requestHeader = requestHeaderRepo.save(requestHeader);
            for (RequestDetail cod: listOfRequestDetail) {
                cod.setRequest_header_id(_requestHeader.getRequest_header_id());
            }
            requestDetailRepo.save(listOfRequestDetail);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "Save Failed!" + ex.getMessage();
        }

        return "Save Successful!";
    }

    public RequestHeader getRequestHeaderById(Long request_header_id){
        return requestHeaderRepo.findOne(request_header_id);
    }

    public List<RequestHeader> getAllRequestHeader(){
        List listOfRequestHeader = requestHeaderRepo.findAll();
        return listOfRequestHeader;
    }

    public void deleteRequestHeader(Long request_header_id) {
        requestHeaderRepo.delete(request_header_id);
    }

    public Set<RequestHeader> getAllRequestHeaderByPendingStatus(){
        Set<RequestHeader> listOfRequestHeader = requestHeaderRepo.listOfAllRequestHeaderByPendingStatus();
        return listOfRequestHeader;
    }

    public Set<RequestHeader> getAllRequestHeaderForGeneralManager(){
        Set<RequestHeader> listOfRequestHeaderGM = requestHeaderRepo.listOfRequestHeaderForGeneralManager();
        return listOfRequestHeaderGM;
    }

    public Set<RequestHeader> getAllRequestHeaderForFinancialController(){
        Set<RequestHeader> listOfRequestHeaderFC = requestHeaderRepo.listOfRequestHeaderForFinancialController();
        return listOfRequestHeaderFC;
    }

    public Set<RequestHeader> getAllRequestHeaderForPurchasingManager(){
        Set<RequestHeader> listOfRequestHeaderPM = requestHeaderRepo.listOfRequestHeaderForPurchasingManager();
        return listOfRequestHeaderPM;
    }

    public Set<RequestHeader> getAllRequestHeaderForDepartmentHead(){
        Set<RequestHeader> listOfRequestHeaderDH = requestHeaderRepo.listOfRequestHeaderForDepartmentHead();
        return listOfRequestHeaderDH;
    }
}
