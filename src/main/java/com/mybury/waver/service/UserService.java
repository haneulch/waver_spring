package com.mybury.waver.service;

import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.domain.Category;
import static com.mybury.waver.domain.Category.createDefaultCategoryFor;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.IdProjection;
import com.mybury.waver.dto.user.UserCreateRequest;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final FileUploadUtils fileUploadUtils;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Long getUserIdByEmail(String email) {
        IdProjection id = userRepository.findIdByEmail(email);
        if (id == null || id.getId() == null) {
            throw new WaverException(ResultCode.NOT_FOUND);
        }
        return id.getId();
    }

    @Transactional
    public void create(UserCreateRequest request) {
        User user = request.user();
        if (request.profileImage() != null) {
            String uploadPath = fileUploadUtils.uploadFile(request.profileImage());
            user.setImgUrl(uploadPath);
        }
        User newUser = userRepository.save(user);
        Category defaultCategory = createDefaultCategoryFor(newUser);
        categoryRepository.save(defaultCategory);
    }

}
