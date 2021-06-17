package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TrelloMapperTestSuite  {

    @Autowired TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards(){
        //given
        TrelloListDto trelloList1 = new TrelloListDto("1", "Test 1", true);
        TrelloListDto trelloList2 = new TrelloListDto("2", "Test 2", false);
        TrelloListDto trelloList3 = new TrelloListDto("3", "Test 3", true);

        List<TrelloListDto> list1 = new ArrayList<>();
        List<TrelloListDto> list2 = new ArrayList<>();

        list1.add(trelloList1);
        list1.add(trelloList2);
        list2.add(trelloList3);

        TrelloBoardDto trelloBoard1 = new TrelloBoardDto("1", "Test Board 1", list1);
        TrelloBoardDto trelloBoard2 = new TrelloBoardDto("2", "Test Board 2", list2);

        List<TrelloBoardDto> board1 = new ArrayList<>();

        board1.add(trelloBoard1);
        board1.add(trelloBoard2);

        //when
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(board1);

        //Then
        assertEquals("1", trelloBoard.get(0).getLists().get(0).getId());
        assertEquals("Test 2", trelloBoard.get(0).getLists().get(1).getName());
        assertEquals(2, trelloBoard.size());
    }

    @Test
    public void testMapToBoardsDto(){
        //given
        TrelloList trelloList1 = new TrelloList("1", "Test 1", true);
        TrelloList trelloList2 = new TrelloList("2", "Test 2", false);
        TrelloList trelloList3 = new TrelloList("3", "Test 3", true);

        List<TrelloList> list1 = new ArrayList<>();
        List<TrelloList> list2 = new ArrayList<>();

        list1.add(trelloList1);
        list1.add(trelloList2);
        list2.add(trelloList3);

        TrelloBoard trelloBoard1 = new TrelloBoard("1", "Test Board 1", list1);
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "Test Board 2", list2);

        List<TrelloBoard> board1 = new ArrayList<>();

        board1.add(trelloBoard1);
        board1.add(trelloBoard2);

        //when
        List<TrelloBoardDto> trelloBoard = trelloMapper.mapToBoardsDto(board1);

        //Then
        assertEquals("1", trelloBoard.get(0).getLists().get(0).getId());
        assertEquals("Test 2", trelloBoard.get(0).getLists().get(1).getName());
        assertEquals(2, trelloBoard.size());
    }

    @Test
    public void testMapToList(){
        //given
        TrelloListDto trelloList1 = new TrelloListDto("1", "Test 1", true);
        TrelloListDto trelloList2 = new TrelloListDto("2", "Test 2", false);
        TrelloListDto trelloList3 = new TrelloListDto("3", "Test 3", true);

        List<TrelloListDto> list1 = new ArrayList<>();

        list1.add(trelloList1);
        list1.add(trelloList2);
        list1.add(trelloList3);

        //when
        List<TrelloList> trelloLists = trelloMapper.mapToList(list1);

        //then
        assertEquals(3, trelloLists.size());
    }

    @Test
    public void testMapToListDto(){
        //given
        TrelloList trelloList1 = new TrelloList("1", "Test 1", true);
        TrelloList trelloList2 = new TrelloList("2", "Test 2", false);
        TrelloList trelloList3 = new TrelloList("3", "Test 3", true);

        List<TrelloList> list1 = new ArrayList<>();

        list1.add(trelloList1);
        list1.add(trelloList2);
        list1.add(trelloList3);

        //when
        List<TrelloListDto> trelloLists = trelloMapper.mapToListDto(list1);

        //Then
        assertEquals(3, trelloLists.size());

    }

    @Test
    public void testMapToCard(){
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card1", "desc1", "up", "01");

        //when
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("card1", trelloCard.getName());
        assertEquals("desc1", trelloCard.getDescription());
    }

    @Test
    public void testMapToCardDto(){
        //given
        TrelloCard trelloCard = new TrelloCard("card1", "desc1", "up", "01");

        //when
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("card1", trelloCardDto.getName());
        assertEquals("desc1", trelloCardDto.getDescription());
    }
}
