import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './metric-data.reducer';
import { IMetricData } from 'app/shared/model/metric-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMetricDataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MetricData = (props: IMetricDataProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { metricDataList, match, loading } = props;
  return (
    <div>
      <h2 id="metric-data-heading" data-cy="MetricDataHeading">
        Metric Data
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Metric Data
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {metricDataList && metricDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Time Stamp</th>
                <th>Measure Value</th>
                <th>Name</th>
                <th>Status</th>
                <th>Metric</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {metricDataList.map((metricData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${metricData.id}`} color="link" size="sm">
                      {metricData.id}
                    </Button>
                  </td>
                  <td>{metricData.timeStamp ? <TextFormat type="date" value={metricData.timeStamp} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{metricData.measureValue}</td>
                  <td>{metricData.name}</td>
                  <td>{metricData.status ? <Link to={`status/${metricData.status.id}`}>{metricData.status.id}</Link> : ''}</td>
                  <td>{metricData.metric ? <Link to={`metric/${metricData.metric.id}`}>{metricData.metric.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${metricData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${metricData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${metricData.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Metric Data found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ metricData }: IRootState) => ({
  metricDataList: metricData.entities,
  loading: metricData.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetricData);
