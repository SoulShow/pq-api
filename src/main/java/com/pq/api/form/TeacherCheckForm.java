package com.pq.api.form;

import com.pq.api.dto.AgencyClassDto;
import com.pq.common.util.OtherUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author liutao
 */
public class TeacherCheckForm implements Serializable {

    private static final long serialVersionUID = 1592708946158411886L;
    private List<AgencyClassDto> classList;

    public List<AgencyClassDto> getClassList() {
        return classList;
    }

    public void setClassList(List<AgencyClassDto> classList) {
        this.classList = classList;
    }
}
