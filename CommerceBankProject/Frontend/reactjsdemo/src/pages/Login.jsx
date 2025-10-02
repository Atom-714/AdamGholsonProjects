import React, {useEffect, useState} from 'react';
import { useNavigate } from "react-router-dom";
import './Login.css'; // Import CSS file


function Login() {

    useEffect(()=>{
        fetch(" http://localhost:8081/logout", {method:"DELETE"})
      },[])

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const submitUser = async (e)=>{
        e.preventDefault();
        console.log("sending data ", username, password);

        try {
            const response = await fetch(" http://localhost:8081/login", {
                method:"POST",
                headers:{
                    "Content-Type" : "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })

            });
            const data = await response.json();
            console.log("Backend response:", data);
            console.log("Login successful:")
            navigate("/mainpage");

        } catch(err) {
            setError("Invalid username or password.");
        }
    }

    return (
        <div className="login-background"
             style={{
                 position: "fixed",
                 top: 0,
                 left: 0,
                 width: "100vw",
                 height: "100vh",
                 backgroundImage: "url('src/assets/CommerceBank.jpg')",
                 backgroundSize: "cover",
                 backgroundPosition: "center",
                 backgroundRepeat: "no-repeat",
                 display: "flex",
                 justifyContent: "center",
                 alignItems: "center"}}>
            <div className="login-form-container">
                <form onSubmit={submitUser}>
                    {error && <p style={{color: "red"}}>{error}</p>}
                    <div className="form-group">
                        <label htmlFor="username">{/*Username:*/}</label>
                        {/*<input id="username" type="text" placeholder="Enter your username" />*/}
                        <input id="username" type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">{/*Password:*/}</label>
                        {/*<input id="password" type="password" placeholder="Enter your password" />*/}
                        <input id="password" type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                    <button type="submit" className="login-button">Login</button>
                </form>

            </div>
        </div>
    );

}

export default Login;
