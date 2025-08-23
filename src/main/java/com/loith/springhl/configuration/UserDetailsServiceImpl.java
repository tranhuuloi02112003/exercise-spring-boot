package com.loith.springhl.configuration;

import com.loith.springhl.entity.UserEntity;
import com.loith.springhl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Phương thức này được Spring Security gọi khi có request đăng nhập.
     * Nhiệm vụ: Load thông tin người dùng từ DB dựa trên username.
     *
     * @param username - username được nhập từ form login
     * @return UserDetails - đối tượng custom chứa thông tin user (id, username, password, role,...)
     * @throws UsernameNotFoundException - nếu không tìm thấy user trong DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Lấy user từ DB theo username
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Map dữ liệu từ UserEntity sang UserDetailsCustom vì method này trả về UserDetails (class tuỳ chỉnh để Spring Security hiểu)
        UserDetailsCustom userDetailsCustom = new UserDetailsCustom();
        userDetailsCustom.setId(userEntity.getId());
        userDetailsCustom.setUsername(userEntity.getUsername());
        userDetailsCustom.setPassword(userEntity.getPassword());

        return userDetailsCustom;
    }
}
