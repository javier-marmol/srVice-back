package com.javier.srvice.presentedTo.infrastructure.controller.dto.output;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.javier.srvice.job.infrastructure.controller.dto.output.JobOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PresentedToOutputDto {
    private Integer id;
    private Double suggestedPrice;
    private EmployeeOutputDto employeeOutputDto;
    private JobOutputDto jobOutputDto;

    public PresentedToOutputDto(PresentedTo candidate){
        this.setId(candidate.getId());
        this.setEmployeeOutputDto(new EmployeeOutputDto(candidate.getEmployee()));
        this.setJobOutputDto(new JobOutputDto(candidate.getJob()));
        this.setSuggestedPrice(candidate.getSuggestedPrice());
    }
}
