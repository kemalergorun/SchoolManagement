package com.techproed.schoolmanagementbackendb326.controller.business;

import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('Admin','Dean')")
    public ResponseMessage<EducationTermResponse>save(
            @Valid @RequestBody EducationTermRequest educationTermRequest){
        return educationTermService.save(educationTermRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @GetMapping("/getAll")
    public List<EducationTermResponse>getAllEducationTerms(){
        //return educationTermService.getAllEducationTerms();
        return null;
    }
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @PutMapping("/update/{educationTermId}")
    public ResponseMessage<EducationTermResponse>updateEducationTerm(
            @Valid @RequestBody EducationTermRequest educationTermRequest,
            @PathVariable Long educationTermId){
        return educationTermService.updateEducationTerm(educationTermRequest,educationTermId);

    }
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @GetMapping("/{educationTermId}")
    public EducationTermResponse getEducationTerm(Long educationTermId){
         return educationTermService.getEducationTermById(educationTermId);


    }
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @GetMapping("/getByPage")
    public Page<EducationTermResponse>getByPage(
            @RequestParam (value = "page",defaultValue = "0") int page,
            @RequestParam (value = "size",defaultValue = "10") int size,
            @RequestParam (value = "sort",defaultValue = "term") String sort,
            @RequestParam (value = "type",defaultValue = "desc") String type){
        return educationTermService.getByPage(page,size,sort,type);
    }
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @DeleteMapping("/delete/{educationTermId}")
    public ResponseMessage deleteEducationTerm(@PathVariable Long educationTermId){
        return educationTermService.deleteById(educationTermId);
    }




}
