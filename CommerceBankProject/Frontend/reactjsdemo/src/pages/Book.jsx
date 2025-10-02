
import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
 

function Book() {

    const[book, setBook] = useState({
        title:'',
        author:'',
      });

    const navigate = useNavigate();  


    const submitBook =(e)=>{
        e.preventDefault();
        fetch(" http://localhost:8081/book", {
          method:"POST",
          headers:{
            "Content-Type" : "application/json"
          },
          body: JSON.stringify(book)
        })
        .then(res=>{
            console.log(1,res);
            if(res.status === 201){
              return res.json();
            }else{
              return null;
            }
          })
        .then(res=>{
          console.log(res)
          if(res!==null){
            navigate("/home");
          }else{
            alert('fails');
          }
       
        });
   
    }  

    const changeValue=(e)=>{
        console.log(e);
        setBook({
         ...book, [e.target.name]:e.target.value  
        });
        console.log(e.target.name + " name "  );
        console.log(e.target.value + " value " );
    }


    return (
        <div>
       
            <Form onSubmit = {submitBook}>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Title</Form.Label>
                    <Form.Control type="text" placeholder="Enter title" onChange = {changeValue} name="title" />
                </Form.Group>

                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Author</Form.Label>
                    <Form.Control type="text" placeholder="Enter Author" onChange = {changeValue} name="author"/>
                </Form.Group>

                <Button variant="primary" type="submit">
                    Submit  
                </Button>
            </Form>
        </div>
      );
 
}

export default Book;
