package dao;

import java.util.List;

import model.Time;

public interface ITimesDao {
	public List<Time> getAllTimes();
	public Time getTimesByCod(int cod);
}
