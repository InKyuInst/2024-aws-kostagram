import { Box, Button, Divider, TextField, Typography } from "@mui/material";
import { useForm } from "react-hook-form";
import { oauthAPI } from "../../api/services/oauth";
import { useParams } from "react-router-dom";
import { setCookie } from "../../utils/cookieUtil";

const EamilVerification = ({ noEmailUser }) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setError,
    clearErrors,
  } = useForm();

  const emailVerification = async(data) => {
    try {
      noEmailUser.email = data.email
      const response = await oauthAPI.emailLogin(noEmailUser);
      console.log(response);
      if (response.data.accessToken) {
        setCookie("accessToken", response.data.accessToken, { path: "/" });
        window.location.href = "/";
      }
    } catch (error) {
      console.log(error);
      setError("email", { type: "manual", message: error.response.data });
    }
  };

  return (
    <Box
      component="form"
      noValidate
      sx={{ p: 5 }}
      onSubmit={handleSubmit(emailVerification)}
    >
      <Typography component="h1" variant="h6" gutterBottom>
        로그인
      </Typography>
      <Divider sx={{ my: 2 }} />
      <TextField
        label="Email address"
        id="email"
        name="email"
        placeholder="your email address"
        variant="standard"
        fullWidth
        margin="normal"
        type="email"
        focused
        color="main"
        autoComplete="email"
        {...register("email", { required: "이메일은 필수 입력값입니다." })}
        error={errors.email ? true : false}
        helperText={errors.email && errors.email.message}
      />
      <Button type="submit" variant="contained" color="sub" fullWidth>
        인증
      </Button>
    </Box>
  );
};

export default EamilVerification;
