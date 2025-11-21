import axios from 'axios';
import { useState, type ChangeEvent } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function UserLogin() {

  const [user, setUser] = useState({
    username: '',
    password: '',
    type: 'admin'
  });



  const changeValue= (e: ChangeEvent<HTMLInputElement>)=>{
    console.log(e);
    setUser({
     ...user, [e.target.name]:e.target.value  
    });
    console.log(e.target.name + " name "  );
    console.log(e.target.value + " value " );
  }

    const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();  
    try {
      const response = await axios.post('/api/user/login', user);
      console.log('User saved:', response.data);
      alert('Logged In Successfully!');
      setUser({ username: '',password: '', type: 'admin' });
      window.location.reload();
    } catch (error) {
      console.error('Error saving user:', error);
      alert('Failed to login.');
    }
  };
  

    return (
    <Form onSubmit={handleSubmit}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Username</Form.Label>
        <Form.Control type="input" placeholder="username" name="username" onChange={changeValue}/>
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Password</Form.Label>
        <Form.Control type="input" placeholder="password" name="password" onChange={changeValue}/>
      </Form.Group>
      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
}
export default UserLogin;