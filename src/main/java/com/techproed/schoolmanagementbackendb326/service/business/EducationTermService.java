package com.techproed.schoolmanagementbackendb326.service.business;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.EducationTermMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.business.EducationTermRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EducationTermService {

    private final EducationTermMapper educationTermMapper;

    private final EducationTermRepository educationTermRepository;

    private final PageableHelper pageableHelper;

    public ResponseMessage<EducationTermResponse> save(@Valid EducationTermRequest educationTermRequest) {
        //validation
        validateEducationTermDates(educationTermRequest);
        //todo write mappers dto to entity entity to dto
        EducationTerm educationTerm=
        educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);

        EducationTerm savedEducationTerm=educationTermRepository.save(educationTerm);

        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest){
        //validate req by reg/st
        validateEducationTermDatesForRequest(educationTermRequest);
        //only one term can exist in year
        if (educationTermRepository.existsByTermAndYear(
                educationTermRequest.getTerm(),
                educationTermRequest.getStartDate().getYear())){
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);

        }

        //validate not to have any conflict with other terms
        educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
                .forEach(educationTerm -> {if(!educationTerm.getStartDate().isAfter(educationTermRequest.getEndDate())
                || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
        }
    });





    }
    private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest){

        if (educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new ConflictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ConflictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }


    public ResponseMessage<EducationTermResponse> updateEducationTerm(@Valid EducationTermRequest educationTermRequest, Long educationTermId) {
//check if exist
        isEducationTermExist(educationTermId);

        validateEducationTermDatesForRequest(educationTermRequest);

        EducationTerm term=educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        term.setId(educationTermId);
        //save to db
        //return by mapping it to dto
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(educationTermRepository.save(term)))
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public EducationTerm isEducationTermExist(Long educationTermId){
        return educationTermRepository.findById(educationTermId)
                .orElseThrow(()->new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE));
    }

    public Page<EducationTermResponse> getByPage(int page, int size, String sort, String type) {
        Pageable pageable=pageableHelper.getPageable(page,size,sort,type);
        //fetch paginated and sorted data from db
        Page<EducationTerm>educationTerms=educationTermRepository.findAll(pageable);
        //use mapper
        return educationTerms.map(educationTermMapper::mapEducationTermToEducationTermResponse);

    }

    public ResponseMessage deleteById(Long educationTermId) {
        isEducationTermExist(educationTermId);
        educationTermRepository.deleteById(educationTermId);
        return ResponseMessage.builder()
                .message(SuccessMessages.EDUCATION_TERM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
