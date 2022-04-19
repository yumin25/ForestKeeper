import logo from "../../res/img/logo.png";
import "./Login.css";
import { useState } from "react";

function Login() {
  const [email, setEmail] = useState("");
  const onEmailHandler = (e) => {
    setEmail(e.target.value);
  };

  const [password, setPassword] = useState("");
  const onPasswordHandler = (e) => {
    setPassword(e.target.value);
  };

  return (
    <div style={{ margin: "10vh" }}>
      <div style={{ display: "flex", alignItems: "center", flexDirection: "column" }}>
        <img style={{ width: "70vw", maxWidth: "400px" }} src={logo} alt="logo" />
        <div style={{ marginBottom: "0.5rem" }}>
          <p style={{ color: "#69696C", marginBottom: "0.5rem" }}>이메일</p>
          <input type="email" className="input" placeholder="forest@keeper.com" onChange={onEmailHandler} />
        </div>
        <div style={{ marginBottom: "2rem" }}>
          <p style={{ color: "#69696C", marginBottom: "0.5rem" }}>비밀번호</p>
          <input type="password" className="input" placeholder="********" onChange={onPasswordHandler} />
        </div>
        <button
          style={{
            backgroundColor: "#37CD8D",
            border: "none",
            borderRadius: "2rem",
            width: "14rem",
            height: "2.5rem",
            color: "white",
            marginBottom: "0.5rem",
          }}
        >
          로그인
        </button>
        <button
          style={{
            borderColor: "#37CD8D",
            backgroundColor: "#FFFFFF",
            borderStyle: "solid",
            borderWidth: "1px",
            borderRadius: "2rem",
            width: "14rem",
            height: "2.5rem",
          }}
        >
          회원가입
        </button>
      </div>
    </div>
  );
}

export default Login;
