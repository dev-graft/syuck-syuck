import React, {useEffect, useState} from 'react'
import axios, {AxiosResponse} from 'axios'

interface IGoodWord {
    id: string,
    content: string,
    createAt: string | Date
    updateAt: string | Date
}

const getGoodWords: () => Promise<AxiosResponse<IGoodWord[]>> = () => {
    return axios.get<IGoodWord[]>("http://localhost:8080/good-words")

}

const GoodWordPage = () => {
    const [loading, setLoading] = useState(true)
    const [goodWords, setGoodWords]: [IGoodWord[], (goodWords: IGoodWord[]) => void] = useState<IGoodWord[]>([])

    useEffect(() => {
        const loadResult = async () => {
            await getGoodWords().then(({data}) => {
                setGoodWords(data)
                setLoading(false)
            })
        }

        loadResult();
    }, [])

    return (
        <div>
            {loading && <div>Loading</div>}
            {/*{goodWords && {*/}

            {/*}}*/}
        </div>
    )
}

export default GoodWordPage