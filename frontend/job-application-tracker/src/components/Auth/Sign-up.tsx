import { Link } from 'react-router-dom';
import loginImage from '../../assets/images.jpeg';
import '../../login.css';

function Signup() {
    const handleSignup = async (e) => {
        e.preventDefault();
        console.log("SIGNUP SUBMITTED");

        try {
            const response = await fetch('http://localhost:8080/api/v1/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    name: e.target.inputName4.value,
                    phone: e.target.inputPhone4.value,
                    email: e.target.inputEmail4.value,
                    password: e.target.inputPassword4.value,
                    birthDate: e.target.inputBirthday4.value,
                }),
            });
            const data = await response.json();
            if (response.ok) {
                console.log('Signup successful:', data);
            }
            console.log(data.message);
        } catch (error) {
            console.error('Error signing up:', error);
        }
    }
    return (
        <>
            <div className="container-fluid p-0">
                <div className="row min-vh-100 g-0">
                    <div className="col-md-6 p-0">
                        <img
                            src={loginImage}
                            alt="Signup"
                            className="w-100 h-100 object-fit-cover"
                        />
                    </div>
                    <div className="col-md-6 d-flex align-items-center justify-content-center">

                        <form className="w-75" onSubmit={handleSignup}>
                            <h1 className='form-header'>Sign Up</h1>

                            <div className="mb-3">
                                <input type="text" className="form-control" id="inputName4" placeholder='Full Name' />
                            </div>
                            <div className="mb-3">
                                <div className="input-group">
                                    <span className="input-group-text">+20</span>
                                    <input
                                        type="tel"
                                        className="form-control"
                                        id="inputPhone4"
                                        placeholder="Phone"
                                    />
                                </div>
                            </div>
                            <div className="mb-3">
                                <input type="email" className="form-control" id="inputEmail4" placeholder='Email' />
                            </div>
                            <div className="mb-3">
                                <input type="password" className="form-control" id="inputPassword4" placeholder='Password' />
                            </div>

                            <div className="mb-3">
                                <label htmlFor="inputBirthday4" className="form-label">
                                    Birthday
                                </label>
                                <input
                                    type="date"
                                    className="form-control"
                                    id="inputBirthday4"
                                />
                            </div>
                            <div className="mb-3">
                                <Link to="/login" className="form-label">
                                    Already have an account?
                                </Link>
                            </div>

                            <div className="row  g-2">
                                <div className="col-auto">
                                    <button type="submit" className="btn btn-primary">
                                        Sign up
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Signup;