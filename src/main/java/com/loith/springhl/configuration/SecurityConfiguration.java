package com.loith.springhl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Đánh dấu đây là class cấu hình Spring
@EnableWebSecurity // Bật Spring Security cho ứng dụng
public class SecurityConfiguration {

    /**
     * Định nghĩa SecurityFilterChain (chuỗi filter chính của Spring Security).
     * Đây là nơi cấu hình cách app xử lý request: xác thực, phân quyền, login, logout...
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Tạm thời disable CSRF (Cross Site Request Forgery).
                // Trong REST API, thường dùng JWT nên có thể disable.
                .csrf(AbstractHttpConfigurer::disable)

                // Cấu hình rule cho request
                .authorizeHttpRequests(auth -> auth
                        // ví dụ: cho phép truy cập public 1 API mà không cần login
                        // .requestMatchers("/api/users").permitAll()

                        // mặc định: tất cả request khác đều cần xác thực
                        .anyRequest().authenticated())

                // Tắt form login mặc định (UI login của Spring Security)
                .formLogin(AbstractHttpConfigurer::disable)

                // Dùng HTTP Basic (dùng Authorization header với username + password)
                // Thích hợp để test nhanh với Postman hoặc curl
                .httpBasic(Customizer.withDefaults())
                // build ra SecurityFilterChain
                .build();
    }

    /**
     * Đây là ví dụ tạo UserDetailsService lưu user trong bộ nhớ (InMemory).
     * Mặc định dùng để test nhanh (username: admin, password: admin).
     * Sau này bạn sẽ thay bằng CustomUserDetailsService lấy user từ DB.
     */
   /* @Bean
    UserDetailsService userDetailsService() {
        UserDetails userDetails =
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(userDetails);
    }*/

    /**
     * Định nghĩa PasswordEncoder dùng để mã hoá mật khẩu.
     * Spring Security bắt buộc mật khẩu phải được mã hoá khi lưu.
     * Ở đây dùng BCrypt (an toàn, thường dùng trong thực tế).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
