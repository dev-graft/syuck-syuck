import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useRouter} from 'next/router';


const theme = createTheme();

export default function tokenAccess() {
    console.log("안녕!")
    const router = useRouter();
    console.log('router', router)
    console.log(router.query['Authorization'])
    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
            </Container>
        </ThemeProvider>
    );
}