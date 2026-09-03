import { Link, useNavigate} from 'react-router-dom';
import { useEffect, useState } from 'react';
import loginImage from '../../assets/images.jpeg';
import '../../login.css';

function Login() {
    const navigate = useNavigate();
    const [message, setMessage] = useState('Loading...');
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/v1/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: e.target.inputEmail4.value,
                    password: e.target.inputPassword4.value,
                }),
            });
            const data = await response.json();
            setMessage(data.message);
            if (response.ok) {
                console.log('Login successful:', data);
            }
        } catch (error) {
            setMessage('Error logging in');
            console.error('Error logging in:', error);
        }
    }
    return (
        <>
            <div className="container-fluid p-0">
                <div className="row min-vh-100 g-0">
                    <div className="col-md-6 p-0">
                        <img
                            src={loginImage}
                            alt="Login"
                            className="w-100 h-100 object-fit-cover"
                        />
                    </div>
                    <div className="col-md-6 d-flex align-items-center justify-content-center">

                        <form className="w-75" onSubmit={handleLogin}>
                            <h1 className='form-header'>Welcome</h1>
                            <div className="mb-3">
                                <input type="email" className="form-control" id="inputEmail4" placeholder='Email' />
                            </div>
                            <div className="mb-3">
                                <input type="password" className="form-control" id="inputPassword4" placeholder='Password' />
                            </div>
                            <div className="mb-3">
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" id="gridCheck" />
                                    <label className="form-check-label" htmlFor="gridCheck" >
                                        Remember me
                                    </label>
                                </div>
                            </div>
                            <div className="mb-3">
                                <Link to="/forgot-password" className="form-label">
                                    Forgot password?
                                </Link>
                            </div>

                            <div className="row  g-2">
                                <div className="col-auto">
                                    <button type="submit" className="btn btn-primary">
                                        Sign in
                                    </button>
                                </div>

                                <div className="col-auto">
                                    <Link to="/signup" className="btn btn-primary">
                                        Sign up
                                    </Link>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Login;