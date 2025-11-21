import axios from 'axios';
import { useState, type ChangeEvent } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

function UserCreate() {

  const [user, setUser] = useState({
    type: '',
    username: '',
    password: ''
  });

  const payload = {
    ...user,
    isAdmin: user.type === 'admin'
  };


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
      const response = await axios.post('/api/user', payload);
      console.log('User saved:', response.data);
      alert('User saved successfully!');
      setUser({ username: '',password: '', type: '' });
    } catch (error) {
      console.error('Error saving user:', error);
      alert('Failed to save user.');
    }
  }
  

    const handleRoleSelect = (role: string | null) => {
    if (role) {
      setUser({
        ...user,
        type: role
      });
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
      <Form.Group className="mb-3">
        <Form.Label>Role: <strong>{user.type || 'None selected'}</strong></Form.Label>
        <DropdownButton 
            id="dropdown-basic-button" 
            title={user.type || 'Select Role'}
            onSelect={handleRoleSelect}
        >
          <Dropdown.Item eventKey="customer">Customer</Dropdown.Item> 
          <Dropdown.Item eventKey="admin">Admin</Dropdown.Item>
        </DropdownButton>
      </Form.Group>
      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
}
export default UserCreate;