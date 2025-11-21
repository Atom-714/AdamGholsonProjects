import axios from 'axios';
import { useState, type ChangeEvent } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function BookList() {

  const [book, setBook] = useState({
    author: '',
    title: '',
    price: ''
  });


  const changeValue= (e: ChangeEvent<HTMLInputElement>)=>{
    console.log(e);
    setBook({
     ...book, [e.target.name]:e.target.value  
    });
    console.log(e.target.name + " name "  );
    console.log(e.target.value + " value " );
  }

    const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();  
    try {
      const response = await axios.post('/api/book', book);
      console.log('Book saved:', response.data);
      alert('Book saved successfully!');
      setBook({ author: '', title: '', price: '' });
    } catch (error) {
      console.error('Error saving book:', error);
      alert('Failed to save book.');
    }
  };

    return (
    <Form onSubmit={handleSubmit}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>title</Form.Label>
        <Form.Control type="input" placeholder="title" name="title" onChange={changeValue}/>
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>author</Form.Label>
        <Form.Control type="input" placeholder="author" name="author" onChange={changeValue}/>
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>price</Form.Label>
        <Form.Control type="input" placeholder="price" name="price" onChange={changeValue}/>
      </Form.Group>
      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
}
export default BookList;