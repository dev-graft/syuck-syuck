import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import Link from '@mui/material/Link';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Image from "next/image";

function Copyright(props: any) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const theme = createTheme();

export default function SignIn() {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            email: data.get('email'),
            password: data.get('password'),
        });
    };

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Image src='/images/background.png' layout={"fill"} objectFit={"cover"} objectPosition={"center"}/>
                <Box
                    sx={{
                        marginTop: 8,
                        padding: 10,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        // border: '1px solid black',
                        zIndex: 1,
                        position: 'absolute',
                        backgroundColor: 'white',
                        borderRadius: '4%'
                    }}
                >
                    <Image src='/images/logo.gif' width={256} height={128}/>
                    <Button
                        href={'http://localhost:8080/oauth2/authorization/google'}
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}>
                        Google Login
                    </Button>
                </Box>
                <Copyright sx={{mt: 8, mb: 4}}/>
            </Container>
        </ThemeProvider>
    );
}