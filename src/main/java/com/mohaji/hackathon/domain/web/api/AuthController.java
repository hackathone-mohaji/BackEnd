package com.mohaji.hackathon.domain.web.api;



import com.mohaji.hackathon.domain.service.AuthService;
import com.mohaji.hackathon.domain.common.error.error.exception.ErrorResponse;
import com.mohaji.hackathon.domain.common.jwt.dto.GeneratedTokenDTO;
import com.mohaji.hackathon.domain.common.jwt.service.JwtProvider;
import com.mohaji.hackathon.domain.web.dto.LoginRequestDTO;
import com.mohaji.hackathon.domain.web.dto.SignUpRequestDTO;
import com.mohaji.hackathon.domain.web.dto.SignUpResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 로직", description = "이메일, 이름, 전화번호, 닉네임, 비밀번호를 입력하면 검증 후 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "이미 존재하는 회원입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {

        SignUpResponseDTO responseDTO = authService.signUp(signUpRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 로직", description = "이메일, 비밀번호를 입력하면 검증 후 로그인을 진행하고 성공하면 access Token, Refresh Token을 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 비밀번호입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "토큰 관련 오류들.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    public ResponseEntity<GeneratedTokenDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        GeneratedTokenDTO generatedTokenDTO = authService.login(loginRequestDTO);
        return ResponseEntity.ok(generatedTokenDTO);

    }



//    @PostMapping("/duplicate/email")
//    @Operation(summary = "이메일 검증 로직", description = "이메일이 중복되었는지 검사합니다",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "사용 가능한 이메일입니다."),
//                    @ApiResponse(responseCode = "400", description = "이미 사용중인 이메일입니다..", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//            })
//    public ResponseEntity<MessageDTO> duplicateEmail(@RequestBody DuplicateDTO.Email duplicateEmailDTO) {
//        String requsetEmail = duplicateEmailDTO.getEmail();
//        boolean isDuplicate = authService.duplicateEmail(requsetEmail);
//        if (isDuplicate) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(MessageDTO.builder()
//                            .message("이미 사용중인 이메일입니다.")
//                            .status(HttpStatus.BAD_REQUEST.value())
//                            .build());
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(MessageDTO.builder()
//                        .message("사용 가능한 이메일 입니다.")
//                        .status(HttpStatus.OK.value())
//                        .build());
//    }


//    @PostMapping("/find/email")
//    @Operation(summary = "이메일 찾기 ", description = "전화번호와 이름을 확인하여 이메일을 조회합니다. ver2에는 본인인증도 구현 예정",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "성공"),
//                    @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//            })
//    public ResponseEntity<FindEmailResponseDTO> findEmail(@Valid @RequestBody FindRequestDTO findRequestDTO) {
//        String name = findRequestDTO.getName();
//        FindEmailResponseDTO findEmailResponseDTO = authService.findEmail(name);
//        return ResponseEntity.ok(findEmailResponseDTO);
//    }

//    @PostMapping("/reset/password")
//    @Operation(summary = "비밀번호 재설정 로직 ", description = "이전에 검증할때 사용했던 이메일도 함께 첨부 바람, 비밀번호 재설정 로직",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "성공"),
//                    @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//            })
//    public ResponseEntity<Void> resetPassword(@Valid @RequestBody FindRequestDTO.ResetPassword resetPasswordDTO) {
//        authService.resetPassword(resetPasswordDTO);
//        return ResponseEntity.status(HttpStatus.OK).build();
//
//    }

//    @DeleteMapping("/delete/member")
//    @Operation(summary = "회원 탈퇴 로직 ", description = "회원 탈퇴 로직(물리적 삭제), ver2에 논리적 삭제로 리펙토링 예정",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "성공"),
//                    @ApiResponse(responseCode = "400", description = "잘못된 Member ID 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//            })
//    public ResponseEntity<MessageDTO> deleteMember() throws ExecutionException, InterruptedException {
//        authService.deleteMember();
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(MessageDTO.builder()
//                        .message("성공적으로 탈퇴되었습니다")
//                        .status(HttpStatus.OK.value())
//                        .build());
//    }

//    @PatchMapping("/tokens")
//    @Operation(summary = "토큰 재발급", description = "Access Token과 남은 기간에 따라 Refresh Token을 재발급 합니다.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "성공"),
//                    @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "400", description = "유효하지 않은 리프레쉬 토큰입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//            })
//    public GeneratedTokenDTO tokenModify(@Valid @RequestBody TokenModifyDTO tokenModifyRequest) {
//        return jwtProvider.reissueToken(tokenModifyRequest.getRefreshToken());
//    }

//    @PatchMapping("/logout")
//    @Operation(summary = "로그아웃", description = "refresh token을 비웁니다. 프론트측에서도 access token을 처리해주세요.")
//    public ResponseEntity<Void> logout() {
//        authService.logout();
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
//
//


}
