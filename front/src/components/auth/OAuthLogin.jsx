import { useLocation, useNavigate, useParams } from "react-router-dom";
import { oauthAPI } from "../../api/services/oauth";
import { useEffect, useState } from "react";
import { setCookie } from "../../utils/cookieUtil";

import EamilVerification from "./EmailVerification";

const OAuthLogin = () => {
  const { provider } = useParams();
  const code = new URLSearchParams(window.location.search).get("code");
  console.log("서버에 전달해야 하는 코드 값 : ", code);

  const [noEmailUser, setNoEmailUser] = useState();

  const oAuthAPI = {
    kakao: (code) => oauthAPI.kakaoSignUp(code),
    google: (code) => oauthAPI.googleSignUp(code),
  };

  const navigate = useNavigate();

  const signIn = async () => {
    try {
      const response = await oAuthAPI[provider](code);
      console.log(response);
      if (response.data.accessToken) {
        setCookie("accessToken", response.data.accessToken, { path: "/" });
        window.location.href = "/";
      } else {
        setNoEmailUser(response.data);
      }
    } catch (error) {
      navigate("/login", {state: error.response.data});
      console.error(error);
    }
  };

  useEffect(() => {
    signIn();
  }, [code]);

  if (noEmailUser) {
    return (<EamilVerification noEmailUser={noEmailUser} />)
  }
  return (<div>로그인 처리 중</div>)
};

export default OAuthLogin;
